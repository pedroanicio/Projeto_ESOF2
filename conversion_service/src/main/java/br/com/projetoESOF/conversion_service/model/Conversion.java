package br.com.projetoESOF.conversion_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "conversion") //mapear a entidade para corresponder a tabela
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //ignorar propriedades que não são necessárias
public class Conversion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "converter", nullable = false, length = 180)
    private String converter;

    @Transient
    private Double value;

    @Transient
    private String from;

    @Transient
    private String to;

    @Transient
    private String environment;

    public Conversion() {}

    public Conversion(Long id, String converter,
                      Double value, String from,
                      String to, String environment) {
        this.id = id;
        this.converter = converter;
        this.value = value;
        this.from = from;
        this.to = to;
        this.environment = environment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConverter() {
        return converter;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversion that = (Conversion) o;
        return Objects.equals(id, that.id) && Objects.equals(converter, that.converter) && Objects.equals(value, that.value) && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(environment, that.environment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, converter, value, from, to, environment);
    }
}
