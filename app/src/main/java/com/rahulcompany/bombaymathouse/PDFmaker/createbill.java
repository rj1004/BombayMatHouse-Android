package com.rahulcompany.bombaymathouse.PDFmaker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.rahulcompany.bombaymathouse.Databases.sell.selldata;
import com.rahulcompany.bombaymathouse.R;
import com.rahulcompany.bombaymathouse.Utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class createbill {
    Context ctx;
    ArrayList<selldata> data;
    public createbill(Context ctx , ArrayList<selldata> data) {
        this.ctx = ctx;
        this.data = data;
    }


    public void makeinvoice() {
        Document document = null;
        try {
            Log.d("tag", 1 + "");
            String folderpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Bombay Mat House/Invoices";
            File folder = new File(folderpath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            Log.d("tag", 2 + "");



            File myFile = new File(folder, data.get(0).getInvoice() + ".pdf");
            OutputStream output = new FileOutputStream(myFile);


            PdfWriter pdfWriter = new PdfWriter(myFile);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            document = new Document(pdfDocument);

            Log.d("tag", 3 + "");

            Paragraph heading = new Paragraph("Bombay Mat House")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(30f);
            Log.d("tag", 4 + "");

            Paragraph invoiceno = new Paragraph("Invoice - "+data.get(0).getInvoice())
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(25f);

            Paragraph p1 = new Paragraph("");

            Paragraph billedto = new Paragraph("Billed to : ")
                    .setFontSize(15f);

            Paragraph billedto1 = new Paragraph("Name : "+data.get(0).getVendor())
                    .setFontSize(15f);
            Paragraph billedto2 = new Paragraph("Contact No . : "+data.get(0).getNo())
                    .setFontSize(15f);


            Paragraph billfrom = new Paragraph("From : ")
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(15f);

            Paragraph billfrom1 = new Paragraph("Bombay Mat House\n(+91 9724114072)")
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(15f);

            Paragraph billfrom2 = new Paragraph("D/31 , Shyam Tulsi Society ")
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(15f);
            Paragraph billfrom3 = new Paragraph("Near Hathibhai Nagar , Diwalipura Road")
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(15f);

            Paragraph billfrom4 = new Paragraph("Vadodara - 390007")
                    .setHorizontalAlignment(HorizontalAlignment.RIGHT)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(15f);
            Log.d("tag", 5 + "");

            float[] columnwidth = {150f, 150f, 150f,150f,150f,150f};
            Table table = new Table(columnwidth);

            Log.d("tag", 6 + "");
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
                    .add(new Paragraph("RATE\n(Rs.)").setFontSize(15f)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph("AMT\n(Rs.)").setFontSize(15f)));
            Log.d("tag", 7+ "");


            for (int i = 0; i < data.size(); i++) {
                selldata a = data.get(i);

                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(a.getCode() + "")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(a.getItem() + "")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(a.getUom() + "")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(a.getQty() + "")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(a.getRate() + "")));
                table.addCell(new Cell()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE)
                        .add(new Paragraph(a.getAmt() + "")));
            }

            table.addCell(new Cell(1, 5).add(new Paragraph("Total Rs . ").setTextAlignment(TextAlignment.RIGHT).setHorizontalAlignment(HorizontalAlignment.RIGHT)));
            table.addCell(new Cell()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .add(new Paragraph(getTotal()+"")));
            Log.d("tag", 8 + "");


            Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(),R.raw.signature);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            byte[] bitmapdata = os.toByteArray();

            ImageData imageData = ImageDataFactory.create(bitmapdata);
            Image image = new Image(imageData);
            image.setWidth(100f);
            image.setHeight(24f);

            Log.d("tag", 9 + "");

            Paragraph sign = new Paragraph("Authorized Signature");

            Paragraph thanks = new Paragraph("Thank you For Your Shopping")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20f);
            Log.d("tag", 10+ "");


            document.add(heading);
            document.add(p1);
            document.add(p1);
            document.add(invoiceno);
            document.add(p1);
            document.add(p1);
            document.add(billfrom);
            document.add(billfrom1);
            document.add(billfrom2);
            document.add(billfrom3);
            document.add(billfrom4);
            document.add(p1);
            document.add(billedto);
            document.add(billedto1);
            document.add(billedto2);
            document.add(p1);
            document.add(p1);
            document.add(table);
            document.add(p1);
            document.add(p1);
            document.add(image);
            document.add(p1);
            document.add(sign);
            document.add(p1);
            document.add(p1);
            document.add(thanks);
            document.close();

            Log.d("tag", 11 + "");
        } catch (Exception e) {
            Log.d("tag", 12 + "");
            Log.d("abcdefgh", e.getMessage());
        }
        finally {
            if (document != null) {
              document.close();
            }
        }
    }


    public double getTotal() {
        double total = 0;
        for (int i = 0; i < data.size(); i++) {
            total = total + data.get(i).getAmt();
        }
        return total;
    }
}
