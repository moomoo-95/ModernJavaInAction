package moomoo.study.java;

import moomoo.study.java.module.Army;
import moomoo.study.java.module.General;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModernJavaInActionMain {
    private static final Logger log = LoggerFactory.getLogger(ModernJavaInActionMain.class);

    public static void main(String[] args) {
        log.debug("하윙");

        General aaa = new General("aaa");
        General bbb = new General("bbb");
        General ccc = new General("ccc");

        Army army1 = new Army("army1");
        Army army2 = new Army("army2");

        army1.setCommander(aaa.getName());
        log.debug("{} : {}", army1.getName(), army1.getCommander().get().getName());
        log.debug("{} : {}", army2.getName(), army2.getCommander().get().getName());
        log.debug("바윙");


    }
}
