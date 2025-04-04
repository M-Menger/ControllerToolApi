package com.menger.controller_tool_api.services;

import com.menger.controller_tool_api.models.Atendimento;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    public List<Atendimento> readAtendimentos(String filePath) {
        List<Atendimento> atendimentos = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Atendimento atendimento = new Atendimento();

                atendimento.setPlaca(getStringValue(row.getCell(4)));
                atendimento.setTipoAtendimento(getStringValue(row.getCell(23)));
                atendimento.setOs(getStringValue(row.getCell(1)));
                atendimento.setData(getStringValue(row.getCell(28)));
                atendimento.setStatus(getStringValue(row.getCell(25)));
                atendimento.setDataAbertura(getStringValue(row.getCell(26)));
                atendimento.setFornecedor(getStringValue(row.getCell(16)));
                atendimento.setEstado(getStringValue(row.getCell(18)));
                atendimento.setCidade(getStringValue(row.getCell(17)));
                atendimento.setCodsap(getStringValue(row.getCell(20)));

                atendimentos.add(atendimento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atendimentos;
    }

    private String getStringValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }
}