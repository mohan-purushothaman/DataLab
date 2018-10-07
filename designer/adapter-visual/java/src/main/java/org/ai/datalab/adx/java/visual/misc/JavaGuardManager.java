///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.ai.datalab.adx.java.visual.misc;
//
//import org.netbeans.api.editor.guards.GuardedSectionManager;
//import org.netbeans.api.editor.mimelookup.MimeLookup;
//import org.netbeans.api.editor.mimelookup.MimeRegistration;
//import org.netbeans.spi.editor.guards.GuardedEditorSupport;
//import org.netbeans.spi.editor.guards.GuardedSectionsFactory;
//import org.netbeans.spi.editor.guards.GuardedSectionsProvider;
//import org.openide.util.Lookup;
//import org.ai.datalab.adx.java.visual.JavaVisualUtil;
//
///**
// *
// * @author Mohan Purushothaman
// */
//@MimeRegistration(mimeType = "text/x-dialog-binding", service = GuardedSectionsFactory.class)
//public class JavaGuardManager extends GuardedSectionsFactory {
//
//    @Override
//    public GuardedSectionsProvider create(GuardedEditorSupport editor) {
//        GuardedSectionsFactory g = MimeLookup.getLookup(JavaVisualUtil.JAVA_MIME_TYPE).lookup(GuardedSectionsFactory.class);
//        return g.create(editor);
//    }
//    
//}
