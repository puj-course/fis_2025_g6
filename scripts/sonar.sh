docker run -d --name sonarqube -p 9000:9000 sonarqube:lts
mvn clean package
rmdir C:\Users\Eliqn\.sonar\cache 
mvn clean verify sonar:sonar "-Dsonar.projectKey=fis2025" "-Dsonar.host.url=http://localhost:9000" "-Dsonar.login=admin" "-Dsonar.password=admin"
