package com.example.spark.rest;

import com.example.spark.config.DatabaseConfig;
import com.example.spark.domain.City;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.struct;

@RequestMapping("/api/v1")
@Controller
public class CityController {
    private final DatabaseConfig databaseConfig;

    public CityController(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Dataset<Row> createStructType(){

        Dataset<Row> datasetCities = databaseConfig.dbConnection("city");

        Dataset<Row> datasetCountries = databaseConfig.dbConnection("country");

        datasetCountries = datasetCountries
                .withColumnRenamed("id", "country_id")
                .withColumnRenamed("name", "country_name")
                .withColumnRenamed("code", "country_code")
                .withColumnRenamed("active", "country_active")
                .withColumnRenamed("created_by", "country_created_by")
                .withColumnRenamed("created_date", "country_created_date")
                .withColumnRenamed("last_modified_by", "country_last_modified_by")
                .withColumnRenamed("last_modified_date", "country_last_modified_date");

        datasetCities = datasetCities.join(datasetCountries, "country_id");

        datasetCities = datasetCities
                .withColumn(
                        "country",
                        struct(
                                col("country_id").as("id"),
                                col("country_name").as("name"),
                                col("country_code").as("code"),
                                col("country_active").as("active"),
                                col("country_created_by").as("created_by"),
                                col("country_created_date").as("created_date"),
                                col("country_last_modified_by").as("last_modified_by"),
                                col("country_last_modified_date").as("last_modified_date")
                        )
                ).drop("country_id",
                        "country_name",
                        "country_code",
                        "country_active",
                        "country_created_by",
                        "country_created_date",
                        "country_last_modified_by",
                        "country_last_modified_date"
                );
        return datasetCities;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities() {
        Encoder<City> encoderDistrict = Encoders.bean(City.class);

        createStructType().createOrReplaceTempView("city");
        Dataset<Row> getCities = createStructType().sparkSession().sql("select * from city");

        return ResponseEntity.ok(getCities.as(encoderDistrict).collectAsList());
    }

    @GetMapping("/citiesBy")
    public ResponseEntity<List<City>> getCitiesByCountryId(@RequestParam("countryId") String countryId) {

        Encoder<City> encoderCity = Encoders.bean(City.class);

        createStructType().createOrReplaceTempView("city");
        Dataset<Row> getCities = createStructType().sparkSession().sql("select * from city where city.country.id == '"+countryId+"'");

        return ResponseEntity.ok(getCities.as(encoderCity).collectAsList());
    }

}
