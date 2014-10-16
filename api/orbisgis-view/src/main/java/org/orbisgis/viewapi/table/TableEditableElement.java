package org.orbisgis.viewapi.table;

import org.orbisgis.viewapi.edition.EditableElementException;
import org.orbisgis.viewapi.edition.EditableSource;

import java.util.Set;
import java.util.SortedSet;

/**
 * @author Nicolas Fortin
 */
public interface TableEditableElement extends EditableSource {
    // Properties names
    public static final String PROP_SELECTION = "selection";


    /**
     * @return Primary keys of the selected rows in the table
     */
    public SortedSet<Long> getSelection();

    /**
     * Set the selected rows in the table using primary key values.
     * @param selection Row's id
     */
    public void setSelection(Set<Long> selection);

    /**
     * @return Row number [1-n] of the selected rows
     */
    public SortedSet<Integer> getSelectionTableRow() throws EditableElementException;

    /**
     * Update selection using row number.
     * @param selection Row number [1-n] of the selected rows
     */
    public void setSelectionTableRow(SortedSet<Integer> selection) throws EditableElementException;
}
