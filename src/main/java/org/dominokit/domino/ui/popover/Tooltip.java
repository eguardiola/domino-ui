package org.dominokit.domino.ui.popover;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.Node;
import org.dominokit.domino.ui.utils.BaseDominoElement;
import org.dominokit.domino.ui.utils.DominoElement;
import org.dominokit.domino.ui.utils.ElementUtil;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.gwt.elemento.core.IsElement;

import static elemental2.dom.DomGlobal.document;
import static org.dominokit.domino.ui.popover.PopupPosition.TOP;
import static org.jboss.gwt.elemento.core.Elements.div;

public class Tooltip extends BaseDominoElement<HTMLDivElement, Tooltip> {

    private DominoElement<HTMLDivElement> element = DominoElement.of(div().css("tooltip").attr("role", "tooltip"));
    private DominoElement<HTMLDivElement> arrowElement = DominoElement.of(div().css("tooltip-arrow"));
    private DominoElement<HTMLDivElement> innerElement = DominoElement.of(div().css("tooltip-inner"));
    private PopupPosition popupPosition = TOP;

    public Tooltip(HTMLElement targetElement, String text) {
        this(targetElement, DomGlobal.document.createTextNode(text));
    }

    public Tooltip(HTMLElement targetElement, Node content) {
        element.appendChild(arrowElement);
        element.appendChild(innerElement);
        innerElement.appendChild(content);

        element.style().add(popupPosition.getDirectionClass());

        targetElement.addEventListener(EventType.mouseenter.getName(), evt -> {
            evt.stopPropagation();
            document.body.appendChild(element.asElement());
            element.style().remove("fade", "in");
            element.style().add("fade", "in");
            popupPosition.position(element.asElement(), targetElement);
            position(popupPosition);
        });

        ElementUtil.onDetach(targetElement, mutationRecord -> element.remove());
        targetElement.addEventListener(EventType.mouseleave.getName(), evt1 -> element.remove());
        init(this);
    }

    public static Tooltip create(HTMLElement target, String text) {
        return new Tooltip(target, text);
    }

    public static Tooltip create(HTMLElement target, Node content) {
        return new Tooltip(target, content);
    }

    public static Tooltip create(IsElement element, String text) {
        return new Tooltip(element.asElement(), text);
    }

    public static Tooltip create(IsElement element, Node content) {
        return new Tooltip(element.asElement(), content);
    }

    public Tooltip position(PopupPosition position) {
        this.element.style().remove(popupPosition.getDirectionClass());
        this.popupPosition = position;
        this.element.style().add(popupPosition.getDirectionClass());

        return this;
    }

    @Override
    public HTMLDivElement asElement() {
        return element.asElement();
    }

    public DominoElement<HTMLDivElement> getArrowElement() {
        return arrowElement;
    }

    public DominoElement<HTMLDivElement> getInnerElement() {
        return innerElement;
    }

    public PopupPosition getPopupPosition() {
        return popupPosition;
    }

    public Tooltip setContent(Node content) {
        innerElement.clearElement();
        innerElement.appendChild(content);
        return this;
    }
}
