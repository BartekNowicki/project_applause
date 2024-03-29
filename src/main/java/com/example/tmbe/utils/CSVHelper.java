package com.example.tmbe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.tmbe.dataModel.*;
import jakarta.persistence.Column;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
  public static String TYPE = "text/csv";

  public static boolean hasCSVFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<Player> csvToPlayers(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser =
            new CSVParser(
                fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()); ) {

      List<Player> players = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        Player player =
            new Player(
                Long.parseLong(csvRecord.get("id")),
                Boolean.parseBoolean(csvRecord.get("isChecked")),
                csvRecord.get("firstName"),
                csvRecord.get("lastName"),
                Integer.parseInt(csvRecord.get("strength")),
                csvRecord.get("comment"),
                null);

        players.add(player);
      }

      return players;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public static List<Tournament> csvToTournaments(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser =
            new CSVParser(
                fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()); ) {

      List<Tournament> tournaments = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      // ORIG DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
      DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

      for (CSVRecord csvRecord : csvRecords) {
        Tournament tournament =
            new Tournament(
                Long.parseLong(csvRecord.get("id")),
                csvRecord.get("type"),
                df.parse(csvRecord.get("startDate")),
                df.parse(csvRecord.get("endDate")),
                Integer.parseInt(csvRecord.get("groupSize")),
                csvRecord.get("comment"),
                null);

        tournaments.add(tournament);
      }

      return tournaments;
    } catch (IOException | ParseException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }
}
