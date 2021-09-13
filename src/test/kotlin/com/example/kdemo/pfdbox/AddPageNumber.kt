package com.example.kdemo.pfdbox

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.printing.PDFPageable
import org.apache.pdfbox.printing.PDFPrintable
import java.awt.print.Book
import java.awt.print.PageFormat
import java.awt.print.PrinterJob
import java.io.File
import java.text.MessageFormat
import kotlin.math.min


fun addPageNumber() {
    // val fi
}

fun main() {
    val file = File("D:\\Project\\Seer\\mwms-server\\standard-project\\files\\tmp\\6132008EB2917B70C0A5B4D8.pdf")
    val outFile = File("D:\\Project\\Seer\\mwms-server\\standard-project\\files\\tmp\\6132008EB2917B70C0A5B4D8-out.pdf")
    
    PDDocument.load(file).use { document ->
        val job = PrinterJob.getPrinterJob()
        job.setPageable(PDFPageable(document))
        
        val book = Book()
        
        book.append(PDFPrintable(document), PageFormat(), document.numberOfPages)
        job.setPageable(book)
        
        // addHeader2(
        //     doc = document,
        //     format = " message message message message message message message message message message message message message message message message message message message message message message message message"
        // )
        
        addHead(
            doc = document,
            msg = "页眉 message message message message message message message message message message message message message message message message message message message message message message message message"
        )
        addPage(
            doc = document,
            msg = "- {0} / {1} -"
        )
        addFoot(
            doc = document,
            msg = "页尾 footer footer footer footer"
        )
        
        document.save(outFile)
    }
}

fun stringToStringArray(src: String, length: Int): List<String> {
    //检查参数是否合法
    if (src.isBlank())
        return emptyList<String>()
    val n = (src.length + length - 1) / length //获取整个字符串可以被切割成字符子串的个数
    
    val res = mutableListOf<String>()
    for (i in 0 until n) {
        res += src.substring(i * length, min((i + 1) * length, src.length))
    }
    
    return res
}

private fun addHeader2(
    doc: PDDocument,
    mx: Float = 30f, my: Float = 30f, fontSize: Float = 15.0f,
    hType: HorizontalAlign = HorizontalAlign.LEFT, vType: VerticalAlign = VerticalAlign.TOP,
    format: String, isPager: Boolean = false
) {
    for (page in doc.pages) {
        val font = PDType1Font.TIMES_BOLD
        val pageSize = page.mediaBox
        
        val contentStream = PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)
        contentStream.beginText()
        contentStream.setFont(font, fontSize)
        contentStream.setLeading(fontSize)
        contentStream.newLineAtOffset(mx, pageSize.height - my)
        
        val textList = stringToStringArray(format, 80)
        textList.forEach {
            contentStream.showText(it)
            contentStream.newLine()
        }
        
        contentStream.endText()
        
        contentStream.close()
    }
    
}

fun addHead(
    doc: PDDocument,
    mx: Float = 30f, my: Float = 30f, fontSize: Float = 15.0f,
    msg: String
) {
    append(doc, mx, my, fontSize, hType = HorizontalAlign.LEFT, vType = VerticalAlign.TOP, msg, false)
}

fun addFoot(
    doc: PDDocument,
    mx: Float = 30f, my: Float = 30f, fontSize: Float = 15.0f,
    msg: String
) {
    append(doc, mx, my, fontSize, hType = HorizontalAlign.LEFT, vType = VerticalAlign.BOTTOM, msg, false)
}

fun addPage(
    doc: PDDocument,
    mx: Float = 30f, my: Float = 30f, fontSize: Float = 15.0f,
    msg: String
) {
    append(doc, mx, my, fontSize, hType = HorizontalAlign.RIGHT, vType = VerticalAlign.BOTTOM, msg, true)
}

private fun append(
    doc: PDDocument,
    mx: Float = 30f, my: Float = 30f, fontSize: Float = 15.0f,
    hType: HorizontalAlign = HorizontalAlign.CENTER, vType: VerticalAlign = VerticalAlign.CENTER,
    msg: String, isPager: Boolean = false
) {
    var pageCounter = 1
    
    for (page in doc.pages) {
        val text: String =
            if (isPager)
                MessageFormat.format(msg, pageCounter++, doc.numberOfPages)
            else msg
        val pageSize = page.mediaBox
        val font: PDFont = PDType0Font.load(doc, File("c:/windows/fonts/simhei.ttf"))
        
        val contentStream = PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)
        contentStream.beginText()
        contentStream.setFont(font, fontSize)
        contentStream.setLeading(fontSize)
        
        val tx = when (hType) {
            HorizontalAlign.CENTER ->
                (pageSize.upperRightX - font.getStringWidth(text) / 100f) / 2f
            HorizontalAlign.LEFT ->
                mx
            HorizontalAlign.RIGHT ->
                (pageSize.upperRightX - font.getStringWidth(text) / fontSize / 10) - 2 * mx
        }
        val ty = when (vType) {
            VerticalAlign.TOP ->
                pageSize.height - my
            VerticalAlign.CENTER ->
                pageSize.height / 2
            VerticalAlign.BOTTOM ->
                my
        }
        contentStream.newLineAtOffset(tx, ty)
        
        val textList = stringToStringArray(text, 80)
        textList.forEach {
            contentStream.showText(it)
            contentStream.newLine()
        }
        contentStream.endText()
        contentStream.close()
    }
}

enum class HorizontalAlign {
    CENTER, LEFT, RIGHT
}

enum class VerticalAlign {
    CENTER, TOP, BOTTOM
}