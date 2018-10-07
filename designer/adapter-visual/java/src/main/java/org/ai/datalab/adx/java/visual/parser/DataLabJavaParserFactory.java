///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.ai.datalab.adx.java.visual.parser;
//
//import java.util.Collection;
//import org.netbeans.api.editor.mimelookup.MimeLookup;
//import org.netbeans.api.editor.mimelookup.MimeRegistration;
//import org.netbeans.modules.parsing.api.Snapshot;
//import org.netbeans.modules.parsing.spi.Parser;
//import org.netbeans.modules.parsing.spi.ParserFactory;
//import org.ai.datalab.adx.java.visual.JavaVisualUtil;
//
///**
// *
// * @author Mohan Purushothaman
// */
//@MimeRegistration(mimeType = "text/x-dialog-binding", service = ParserFactory.class)
//public class DataLabJavaParserFactory extends ParserFactory{
//
//    @Override
//    public Parser createParser(Collection<Snapshot> snapshots) {
//       return MimeLookup.getLookup (JavaVisualUtil.JAVA_MIME_TYPE).lookup(ParserFactory.class).createParser(snapshots);
//    }
//    
//}
