package com.rahulcompany.bombaymathouse.PDFmaker;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;


import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.rahulcompany.bombaymathouse.Databases.master.MasterData;
import com.rahulcompany.bombaymathouse.Databases.purchase.purchasedata;
import com.rahulcompany.bombaymathouse.Databases.sell.selldata;
import com.rahulcompany.bombaymathouse.Databases.stock.stockdata;
import com.rahulcompany.bombaymathouse.Databases.stock.stockhelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class createpdf {


    public void master(ArrayList<MasterData> list) {

        try {
            String folderpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bombay Mat House/Master Data";
            File folder = new File(folderpath);
            if (!folder.exists()) {
                folder.mkdirs();
            }


            //Create time stamp
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File myFile = new File(folder, "master_" + timeStamp + ".pdf");

            OutputStream output = new FileOutputStream(myFile);


            PdfWriter pdfWriter = new PdfWriter(myFile);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Paragraph heading = new Paragraph("Bombay Mat House")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(30f);



            Paragraph p = new Paragraph("Material Master")
                    .setFontSize(20f);

            Paragraph p1 = new Paragraph("");

            Paragraph p4 = new Paragraph("Developed By Rahul's & Company")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(15f);



            float[] columnwidth = {150f, 150f, 150f,150f};
            Table table = new Table(columnwidth);

            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("Material Code").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("Material Name").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("UOM").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("IMG").setFontSize(15f)));






            for (int i = 0; i < list.size(); i++) {

                MasterData data = list.get(i);
                long code=data.getCode()+2122000000;
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(code+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getItem())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getUom())));




                File img = new File(data.getImagepath());
                Bitmap bmp = BitmapFactory.decodeFile(img.getPath());
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 10, os);
                ImageData d = ImageDataFactory.create(os.toByteArray());
                Image image = new Image(d);
                image.setWidth(90f);
                image.setHeight(90f);

                table.addCell(new Cell().setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER).add(image));

            }

            document.add(heading);
            document.add(p);
            document.add(p1);
            document.add(p1);
            document.add(table);
            document.add(p1);
            document.add(p1);
            document.add(p4);

            document.close();
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

        }
    public void stock(ArrayList<stockdata> list,String totalprofit) {

        try {
            String folderpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bombay Mat House/Stock Data";
            File folder = new File(folderpath);
            if (!folder.exists()) {
                folder.mkdirs();
            }


            //Create time stamp
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File myFile = new File(folder, "stock_" + timeStamp + ".pdf");

            OutputStream output = new FileOutputStream(myFile);


            PdfWriter pdfWriter = new PdfWriter(myFile);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Paragraph heading = new Paragraph("Bombay Mat House")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(30f);



            Paragraph p = new Paragraph("Material Stock")
                    .setFontSize(20f);

            Paragraph profit = new Paragraph(totalprofit)
                    .setFontSize(20f);

            Paragraph p1 = new Paragraph("");

            Paragraph p4 = new Paragraph("Developed By Rahul's & Company")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(15f);



            float[] columnwidth = {150f, 150f, 150f,150f,150f};
            Table table = new Table(columnwidth);

            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("Material Code").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("Material Name").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("UOM").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("QTY").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("PROFIT").setFontSize(15f)));






            for (int i = 0; i < list.size(); i++) {

                stockdata data = list.get(i);
                long code=data.getCode();
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(code+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getItem())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getUom())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getQty()+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getProfit()+"")));



            }

            document.add(heading);
            document.add(p);
            document.add(p1);
            document.add(profit);
            document.add(p1);
            document.add(p1);
            document.add(table);
            document.add(p1);
            document.add(p1);
            document.add(p4);

            document.close();
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

    }
    public void purchase(ArrayList<purchasedata> list) {

        try {
            String folderpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bombay Mat House/Purchase Data";
            File folder = new File(folderpath);
            if (!folder.exists()) {
                folder.mkdirs();
            }


            //Create time stamp
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File myFile = new File(folder, "purchase_" + timeStamp + ".pdf");

            OutputStream output = new FileOutputStream(myFile);


            PdfWriter pdfWriter = new PdfWriter(myFile);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Paragraph heading = new Paragraph("Bombay Mat House")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(30f);



            Paragraph p = new Paragraph("Purchase")
                    .setFontSize(20f);

            Paragraph p1 = new Paragraph("");

            Paragraph p4 = new Paragraph("Developed By Rahul's & Company")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(15f);



            float[] columnwidth = {150f, 150f, 150f,150f,150f,150f,150f,150f};
            Table table = new Table(columnwidth);

            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("Material Code").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("Material Name").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("DATE").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("VENDOR").setFontSize(15f)));

            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("UOM").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("QTY").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("RATE").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("AMT").setFontSize(15f)));






            for (int i = 0; i < list.size(); i++) {

                purchasedata data = list.get(i);

                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getCode()+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getItem())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getDate())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getVendor())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getUom())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getQty()+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getRate()+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getAmt()+"")));



            }

            document.add(heading);
            document.add(p);
            document.add(p1);
            document.add(p1);
            document.add(table);
            document.add(p1);
            document.add(p1);
            document.add(p4);

            document.close();
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

    }
    public void sell(ArrayList<selldata> list) {

        try {
            String folderpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bombay Mat House/Sell Data";
            File folder = new File(folderpath);
            if (!folder.exists()) {
                folder.mkdirs();
            }


            //Create time stamp
            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File myFile = new File(folder, "sell_" + timeStamp + ".pdf");

            OutputStream output = new FileOutputStream(myFile);


            PdfWriter pdfWriter = new PdfWriter(myFile);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Paragraph heading = new Paragraph("Bombay Mat House")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(30f);



            Paragraph p = new Paragraph("Sell")
                    .setFontSize(20f);

            Paragraph p1 = new Paragraph("");

            Paragraph p4 = new Paragraph("Developed By Rahul's & Company")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(15f);



            float[] columnwidth = {100f, 100f, 100f,100f,100f,100f,100f,100f,100f,100f};
            Table table = new Table(columnwidth);

            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("Material Code").setFontSize(10f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("Material Name").setFontSize(10f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("DATE").setFontSize(10f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("INVOICE").setFontSize(10f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("CUSTOMER NAME").setFontSize(10f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("CUSTOMER NO.").setFontSize(10f)));

            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("UOM").setFontSize(10f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("QTY").setFontSize(10f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("RATE").setFontSize(10f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("AMT").setFontSize(10f)));






            for (int i = 0; i < list.size(); i++) {

                selldata data = list.get(i);

                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getCode()+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getItem())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getDate())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getInvoice()+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getVendor())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getNo())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getUom())));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getQty()+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getRate()+"")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(data.getAmt()+"")));



            }

            document.add(heading);
            document.add(p);
            document.add(p1);
            document.add(p1);
            document.add(table);
            document.add(p1);
            document.add(p1);
            document.add(p4);

            document.close();
        } catch (Exception e) {
            Log.d("exception", e.getMessage());
        }

    }



    }




