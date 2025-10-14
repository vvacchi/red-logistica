package com.uade.tpo.red_logistica;

import org.neo4j.driver.Driver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class testNeo4jConexion implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(testNeo4jConexion.class);
    private final Driver driver;

    public testNeo4jConexion(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void run(String... args) {
        try (var session = driver.session()) {
            var result = session.run("RETURN 'Conexión exitosa con Neo4j' AS msg");
            log.info(result.single().get("msg").asString());
        } catch (Exception e) {
            log.error("Error conectando a Neo4j", e);
        }
    }
}
