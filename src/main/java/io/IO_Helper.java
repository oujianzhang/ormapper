package io;


import annotation.Entity;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IO_Helper {

    private static List<String> getEntityClassnames(Path dirPath) {
        List<String> classnames = new ArrayList<>();
        File dir = new File(dirPath.toString());
        File[] dirContent = dir.listFiles();

        for (File file : dirContent) {
            if (file.isDirectory()) {
                classnames.addAll(getEntityClassnames(file.toPath()));
            } else if (file.isFile()) {
                String pathname = file.getAbsolutePath();
                if (pathname.endsWith(".java")) {
                    String classname =
                            pathname.substring(pathname.indexOf("java")+5,pathname.lastIndexOf(".java")).replace("\\", ".");
                    try {
                        Class entityClass = Class.forName(classname);
                        if(entityClass.isAnnotationPresent(Entity.class)){
                            classnames.add(classname);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classnames;
    }

    public static void main(String[] args) {
        Path dirPath = Paths.get(".", "src", "main", "java");
        for (String str : getEntityClassnames(dirPath)) {
            System.out.println(str);
        }
    }
}