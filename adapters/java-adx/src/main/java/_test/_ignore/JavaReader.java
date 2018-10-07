//package _test._ignore;
////pre-import
//import org.ai.datalab.core.Data;
//import org.ai.datalab.core.ExecutionConfig;
//import org.ai.datalab.core.executor.impl.OneToOneDataProcessor;
////post-import
//
//public class JavaReader extends OneToOneDataProcessor {
//
//    /**
//     * variable declarations start
//     */
//    private final int max = 1000;
//
//    private int start;
//
//    /**
//     * variable declarations end
//     */
//    /**
//     * EXECUTION INIT Start
//     */
//    @Override
//    public void init(ExecutionConfig config) throws Exception {
//        start = 1;
//    }
//
//    /**
//     * EXECUTION INIT end /
//     *
//     */
//    /**
//     * exewcution shutdown start
//     */
//    @Override
//    public void shutdown(ExecutionConfig config) throws Exception {
//        System.out.println("ending");
//    }
//
//    /**
//     * EXECUTION shutdown end
//     */
//    /**
//     * EXECUTE START
//     */
//    @Override
//    public Data process(Data data,ExecutionConfig config) throws Exception {
//        data.setValue("test", null, (start = start + 2));
//        return data;
//    }
//
//    
//}
