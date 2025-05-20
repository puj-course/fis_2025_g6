package com.fis_2025_g6.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;

public class CoverageReportReader {

    public static void main(String[] args) {
        File outputFile = new File("target/site/jacoco/coverage-summary.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            Document doc = loadXmlDocument("target/site/jacoco/jacoco.xml");
            NodeList packageList = doc.getElementsByTagName("package");
            for (int i = 0; i < packageList.getLength(); i++) {
                Element packageElement = (Element) packageList.item(i);
                processPackage(packageElement, writer);
            }
            System.out.println("Resumen de cobertura guardado en: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error al generar el reporte:");
            e.printStackTrace();
        }
    }

    private static Document loadXmlDocument(String path) throws Exception {
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        dbFactory.setValidating(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    private static void processPackage(Element packageElement, BufferedWriter writer) throws IOException {
        NodeList classList = packageElement.getElementsByTagName("class");
        for (int j = 0; j < classList.getLength(); j++) {
            Element classElement = (Element) classList.item(j);
            processClass(classElement, writer);
        }
    }

    private static void processClass(Element classElement, BufferedWriter writer) throws IOException {
        String rawClassName = classElement.getAttribute("name").replace("/", ".");
        writer.write("Class: " + rawClassName + "\n");

        Map<String, int[]> counterMap = getCounters(classElement);

        writeCoverage(writer, counterMap);
        writer.write("\n");
    }

    private static Map<String, int[]> getCounters(Element classElement) {
        Map<String, int[]> counterMap = new LinkedHashMap<>();
        NodeList counters = classElement.getElementsByTagName("counter");
        for (int k = 0; k < counters.getLength(); k++) {
            Element counter = (Element) counters.item(k);
            String type = counter.getAttribute("type");
            int missed = Integer.parseInt(counter.getAttribute("missed"));
            int covered = Integer.parseInt(counter.getAttribute("covered"));

            counterMap.merge(type, new int[]{missed, covered}, (oldVals, newVals) -> {
                oldVals[0] += newVals[0];
                oldVals[1] += newVals[1];
                return oldVals;
            });
        }
        return counterMap;
    }

    private static void writeCoverage(BufferedWriter writer, Map<String, int[]> counterMap) throws IOException {
        for (Map.Entry<String, int[]> entry : counterMap.entrySet()) {
            String type = entry.getKey();
            int missed = entry.getValue()[0];
            int covered = entry.getValue()[1];
            int total = missed + covered;
            int percentage = total > 0 ? (covered * 100) / total : 0;
            writer.write("  " + type + ": " + percentage + "%\n");
        }
    }
}
