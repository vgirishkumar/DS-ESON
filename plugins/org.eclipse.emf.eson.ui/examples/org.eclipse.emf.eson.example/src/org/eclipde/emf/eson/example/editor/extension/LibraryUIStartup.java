/*
 * #%L
 * org.eclipse.emf.eson.ui
 * %%
 * Copyright (C) 2016 Michael Vorburger and others
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */
package org.eclipde.emf.eson.example.editor.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;

/**
 * This is an example how one can use existing editor to open any file type.
 * 
 * @author vgirishkumar
 */
@SuppressWarnings("restriction")
public class LibraryUIStartup implements IStartup {

    @Override
    public void earlyStartup() {
        Display.getDefault().asyncExec(new Runnable() {

            @Override
            public void run() {
                // DSL + Tree Editor
                Set<String> associateFileExtensions = new HashSet<String>();
                associateFileExtensions.add("library");
                addCustomEditorMappingFor("org.eclipse.emf.eson.ui.editor.tree.EFactoryWithTreeEditor",
                        associateFileExtensions);
                // DSL Editor
                addCustomEditorMappingFor("org.eclipse.emf.eson.EFactory", associateFileExtensions);
            }

        });

    }

    /**
     * @param editorId
     */
    private void addCustomEditorMappingFor(String editorId, Set<String> associateFileExtensions) {
        EditorRegistry editorReg = (EditorRegistry) PlatformUI.getWorkbench().getEditorRegistry();

        EditorDescriptor editor = (EditorDescriptor) editorReg.findEditor(editorId);

        IFileEditorMapping[] mappings = editorReg.getFileEditorMappings();
        List<IFileEditorMapping> asList = new ArrayList<IFileEditorMapping>(Arrays.asList(mappings));

        for (String extension : associateFileExtensions) {
            FileEditorMapping mapping = new FileEditorMapping(extension);
            mapping.addEditor(editor);
            mapping.setDefaultEditor(editor);
            asList.add(mapping);
        }

        editorReg.setFileEditorMappings(asList.toArray(new FileEditorMapping[asList.size()]));
    }
}
