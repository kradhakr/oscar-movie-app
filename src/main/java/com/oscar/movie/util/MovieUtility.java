package com.oscar.movie.util;

import com.oscar.movie.model.Movie;
import com.oscar.movie.model.OscarAwards;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieUtility {
    public static List<OscarAwards> csvToOscarAwards(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<OscarAwards> oscarAwardsList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String category = csvRecord.get("Category");
                String nominee = csvRecord.get("Nominee");
                OscarAwards oscarAwards = new OscarAwards(
                        csvRecord.get("Year"),
                        category,
                        nominee,
                        csvRecord.get("Additional Info"),
                        csvRecord.get("Won?").equalsIgnoreCase("YES")? true : false
                );
                oscarAwardsList.add(oscarAwards);
            }

            return oscarAwardsList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static Set<Movie> getMovieSet(List<OscarAwards> oscarAwardsList) {
        Set<Movie> movieSet = new HashSet<>();
        for (OscarAwards oscarAwards : oscarAwardsList) {
            String category = oscarAwards.getCategory();
            if(!category.contains("Actor") && !category.contains("Actress")
                    && !category.contains("Acting") && !category.contains("Directing")
                    && !category.contains("Scientific") && !category.contains("Honorary")
                    && !category.contains("Special")  && !category.contains("Writing")
                    && !category.contains("Engineering Effects")) {
                String nominee = oscarAwards.getNominee();
                if(!nominee.contains("[NOTE:")) {
                    Movie movie = new Movie();
                    movie.setTitle(nominee);
                    movieSet.add(movie);
                }
            }
        }
        return movieSet;
    }
}
