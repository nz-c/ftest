FROM tomcat:9.0
EXPOSE 8080

ADD target/SumService.war /usr/local/tomcat/webapps/

RUN sed -i 's/<\/Host>/<Context docBase=\"SumService.war\" path=\"\/\" reloadable=\"true\"\/><\/Host>/' /usr/local/tomcat/conf/server.xml

RUN chmod 777 /usr/local/tomcat/bin/catalina.sh
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]