/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information. OrbisGIS is
 * distributed under GPL 3 license. It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/> CNRS FR 2488.
 *
 *
 *  Team leader Erwan BOCHER, scientific researcher,
 *
 *  User support leader : Gwendall Petit, geomatic engineer.
 *
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * Copyright (C) 2010 Erwan BOCHER, Pierre-Yves FADET, Alexis GUEGANNO, Maxence LAURENT
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 *
 * or contact directly:
 * erwan.bocher _at_ ec-nantes.fr
 * gwendall.petit _at_ ec-nantes.fr
 */
package org.orbisgis.core.ui.editorViews.toc.actions.cui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.orbisgis.core.images.OrbisGISIcon;

/**
 *
 * An UI Element, which edit and manage UI for one SE element
 *
 *
 * @author maxence
 */
public abstract class LegendUIComponent extends LegendUIAbstractPanel {

	protected float weight; // TODO en faire qqch...
	// The direct parent
	protected LegendUIComponent parent;
	// this.parent is null if this component has to be rendered into its own panel
	// In this case, the initial parent is set to parent before setting it to null
	private LegendUIComponent initialParent;
	// Children list
	private ArrayList<LegendUIComponent> children;
	private ArrayList<LegendUIComponentListener> listeners;
	private String name;
	// Is the (this) UI represents a NULL element (such as no fill) ?
	protected boolean isNullComponent;
	// Panel in which mount UI element
	protected LegendUIAbstractPanel editor;
	// Panel reserved for special
	protected LegendUIAbstractPanel toolbar;
	private JButton nullifier;

	/**
	 *
	 * @param name
	 * @param controller
	 * @param parent
	 * @param weight unused
	 * @param nullable does the edited element be null ?
	 */
	public LegendUIComponent(String name, LegendUIController controller, LegendUIComponent parent, float weight, boolean nullable) {
		super(controller);

		this.weight = weight;
		this.parent = parent;

		this.name = name;

		if (nullable) {
			this.nullifier = new JButton(OrbisGISIcon.DEL);
			nullifier.setMargin(new Insets(0, 0, 0, 0));
			this.nullifier.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					isNullComponent = true;
					turnOff();

				}
			});
		} else {
			this.nullifier = null;
		}

		listeners = new ArrayList<LegendUIComponentListener>();

		this.children = new ArrayList<LegendUIComponent>();

		if (this.parent != null) {
			this.parent.registerChild(this);
		}

		//this.setBorder(BorderFactory.createTitledBorder(name));
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		this.name = name;
	}

	@Override
	public final String toString() {
		if (this.getName() != null) {
			return this.getName();
		} else {
			return this.getClass().getSimpleName();
		}
	}

	public Iterator<LegendUIComponent> getChildrenIterator() {
		return children.iterator();
	}

	/**
	 * Does this component starts a new panel ?
	 * If true, then this component will be accessible through LegendUI TOC
	 * @return true if this has to be laid out in its own panel
	 */
	public boolean isNested() {
		return (initialParent != null);
	}

	/**
	 *  Does this component define anything or not ?
	 */
	public boolean isNull() {
		return isNullComponent;
	}

	/**
	 * @return true if this component has no parent
	 */
	public boolean isTopElement() {
		return parent == null && initialParent == null;
	}

	/**
	 * Return the top-most UIComponent in the current panel
	 */
	public LegendUIComponent getScopeParent() {
		LegendUIComponent current = this;

		System.out.println("Get Scope Parent for : " + current);
		while (current != null && !current.isNested() && !current.isTopElement()) {
			current = current.parent;
			System.out.println("  -> Current : " + current);
		}
		return current;
	}

	/**
	 * @return Component parent
	 */
	public LegendUIComponent getParentComponent() {
		if (this.parent != null) {
			return this.parent;
		} else {
			return this.initialParent;
		}
	}

	/**
	 *
	 * @return Return the symbolizer UI component
	 */
	public LegendUIComponent getTopParent() {
		LegendUIComponent current = this;

		while (current instanceof LegendUISymbolizerPanel == false) {
			current = current.getParentComponent();
		}

		return current;
	}

	public void makeOrphan() {
		LegendUIComponent p = getParentComponent();

		if (p != null) {
			p.removeChildInternal(this);
		}

		this.parent = null;
		this.initialParent = null;
	}

	private void removeChildInternal(LegendUIComponent child) {
		if (children.contains(child)) {
			children.remove(child);
		}
	}

	protected void removeChild(LegendUIComponent child) {
		if (children.contains(child)) {
			child.makeOrphan();
		}
	}

	/**
	 * @param child
	 */
	private void registerChild(LegendUIComponent child) {
		this.children.add(child);
	}

	public void addChild(LegendUIComponent child) {
		this.registerChild(child);
		child.parent = this;
	}

	/**
	 * Bottom to top way to extract a component into a new sub-pane
	 *
	 */
	public void extractFromParent() {
		this.detachFromParent();
		//initialParent.markChildAsExternal(this);
	}

	/**
	 * Top to bottom way to make separate panels
	 * @param child
	 */
	public void extractChild(LegendUIComponent child) {
		//markChildAsExternal(child);
		if (children.contains(child)) {
			child.detachFromParent();
		}
	}

	/**
	 * mark the break between this and its parent
	 */
	private void detachFromParent() {
		if (parent != null && initialParent == null) {
			this.initialParent = this.parent;
			this.parent = null;
		}
	}

	public void unnest() {
		if (parent == null && initialParent != null) {
			this.parent = this.initialParent;
			this.initialParent = null;
		}

		if (this.parent == null) {
			System.out.println("I'm a lonly orpan: " + this);
		}
	}


	/*
	 * Inline all children (recursivly)
	 */
	public void unnestChildren() {
		for (LegendUIComponent child : this.children) {
			if (child.isNested()) {
				child.unnest();
			}
			child.unnestChildren();
		}
	}

	/**
	 *  remove all children (recursivly)
	 */
	public void clear() {
		this.removeAll();
		this.clearListener();
		for (LegendUIComponent child : children) {
			child.clear();
			child.parent = null;
			child.initialParent = null;
		}
		children.clear();
	}

	public void clearListener() {
		listeners.clear();
	}

	public void register(LegendUIComponentListener l) {
		if (!listeners.contains(l)) {
			System.out.println(l + " is now listen to " + this);
			listeners.add(l);
		}
	}

	protected void fireNameChanged() {
		for (LegendUIComponentListener l : listeners) {
			l.nameChanged();
		}
	}

	/**
	 * Deep-build the interface from this to button
	 */
	protected void mountComponentForChildren() {
		this.removeAll();

		/*   _______________
		 *  |               |
		 *  |    toolbar    |
		 *  |_______________|
		 *  |               |
		 *  |    editor     |
		 *  |_______________|
		 */

		toolbar = new LegendUIAbstractPanel(controller);
		//toolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));

		editor = new LegendUIAbstractPanel(controller);

		this.add(toolbar, BorderLayout.NORTH);
		this.add(editor, BorderLayout.SOUTH);

		// List of null parameters (will be listed (by name) in the toolbar)
		ArrayList<LegendUIComponent> nullList = new ArrayList<LegendUIComponent>();

		Iterator<LegendUIComponent> it = this.getChildrenIterator();
		while (it.hasNext()) {
			LegendUIComponent child = it.next();

			// Child is not null => create its interface
			if (!child.isNull()) {
				child.mountComponentForChildren();
				child.mountComponent();
			} else {
				// Child is null => in the list of unused parameters
				nullList.add(child);
			}
		}

		if (nullList.size() > 0) {
			/*
			 * For each null parameter, create at SetUpLabel, and puch it in the toolbar
			 */
			Iterator<LegendUIComponent> nullIt = nullList.iterator();
			while (nullIt.hasNext()) {
				LegendUIComponent next = nullIt.next();
				toolbar.add(new SetUpParam(next));
			}

		}

		if (nullifier != null) {
			// Add a button to nullifiy this component (so it will be listed in its parent toolbar)
			toolbar.add(nullifier);
		}

		this.mountComponent();
	}

	private class SetUpParam extends JLabel {

		LegendUIComponent comp;

		public SetUpParam(final LegendUIComponent comp) {
			super("<html><u><i>" + comp.getName() + "</i></u></html>");
			this.comp = comp;
			setForeground(new Color(160, 160, 160));
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					comp.turnOn();
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}
			});
		}
	};

	/**
	 *
	 * @return
	 */
	public abstract Icon getIcon();

	/**
	 * start from this.removeAll() and go on
	 */
	protected abstract void mountComponent();

	protected abstract void turnOff();
	protected abstract void turnOn();


	public abstract Class getEditedClass();

}
