package com.fis_2025_g6.util;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;

public class CoverageReportReader {
    public static void main(String[] args) {
        File outputFile = new File("target/site/jacoco/coverage-summary.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            File xmlFile = new File("target/site/jacoco/jacoco.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbFactory.setValidating(false);

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList packageList = doc.getElementsByTagName("package");

            for (int i = 0; i < packageList.getLength(); i++) {
                Element packageElement = (Element) packageList.item(i);

                NodeList classList = packageElement.getElementsByTagName("class");

                for (int j = 0; j < classList.getLength(); j++) {
                    Element classElement = (Element) classList.item(j);

                    String rawClassName = classElement.getAttribute("name").replace("/", ".");
                    String fullClassName = rawClassName;  // Ya es el nombre completo con el paquete
                    writer.write("Class: " + fullClassName + "\n");

                    Map<String, int[]> counterMap = new LinkedHashMap<>();

                    NodeList counters = classElement.getElementsByTagName("counter");
                    for (int k = 0; k < counters.getLength(); k++) {
                        Element counter = (Element) counters.item(k);
                        String type = counter.getAttribute("type");
                        int missed = Integer.parseInt(counter.getAttribute("missed"));
                        int covered = Integer.parseInt(counter.getAttribute("covered"));

                        if (!counterMap.containsKey(type)) {
                            counterMap.put(type, new int[]{missed, covered});
                        } else {
                            int[] current = counterMap.get(type);
                            current[0] += missed;
                            current[1] += covered;
                        }
                    }

                    for (Map.Entry<String, int[]> entry : counterMap.entrySet()) {
                        String type = entry.getKey();
                        int missed = entry.getValue()[0];
                        int covered = entry.getValue()[1];
                        int total = missed + covered;
                        int percentage = total > 0 ? (covered * 100) / total : 0;
                        writer.write("  " + type + ": " + percentage + "%\n");
                    }

                    writer.write("\n");
                }
            }

            System.out.println("Resumen de cobertura guardado en: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Error al generar el reporte:");
            e.printStackTrace();
        }
    }
}
