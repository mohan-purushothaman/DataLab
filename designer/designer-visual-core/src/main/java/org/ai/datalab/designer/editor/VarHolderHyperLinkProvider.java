package org.ai.datalab.designer.editor;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.ai.datalab.designer.editor;
//
//
//import javax.swing.text.Document;
//import org.netbeans.api.editor.mimelookup.MimeRegistration;
//import org.netbeans.api.lexer.Token;
//import org.netbeans.api.lexer.TokenHierarchy;
//import org.netbeans.api.lexer.TokenSequence;
//import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProvider;
//import org.openide.DialogDisplayer;
//import org.openide.NotifyDescriptor;
//
//@MimeRegistration(mimeType = SimpleEditor.LANG_MIME_TYPE, service = HyperlinkProvider.class)
//public class VarHolderHyperLinkProvider implements HyperlinkProvider {
//
//    private String target;
//
//    @Override
//    public boolean isHyperlinkPoint(Document doc, int offset) {
//        return verifyState(doc, offset);
//    }
//
//    public boolean verifyState(Document doc, int offset) {
//        TokenHierarchy hi = TokenHierarchy.get(doc);
//        TokenSequence<VarLangToken> ts = hi.tokenSequence();
//        if (ts != null) {
//            ts.move(offset);
//            ts.moveNext();
//            Token<VarLangToken> tok = ts.token();
//
//            if (tok.id() == VarLangToken.VARIABLE) {
//                String fullText = tok.text().toString();
//                target = fullText.substring(1, target.length() - 1);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public int[] getHyperlinkSpan(Document document, int offset) {
//        if (verifyState(document, offset)) {
//            return new int[]{offset, offset + target.length()};
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void performClickAction(Document document, int offset) {
//        if (verifyState(document, offset)) {
//            NotifyDescriptor.Message msg = new NotifyDescriptor.Message(target);
//            DialogDisplayer.getDefault().notify(msg);
//        }
//    }
//
//}
//
