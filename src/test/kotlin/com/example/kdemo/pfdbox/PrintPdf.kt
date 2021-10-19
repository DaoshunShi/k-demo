package com.example.kdemo.pfdbox

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.printing.Orientation
import org.apache.pdfbox.printing.PDFPageable
import org.apache.pdfbox.printing.PDFPrintable
import java.awt.print.Book
import java.awt.print.PageFormat
import java.awt.print.Paper
import java.awt.print.PrinterJob
import java.io.File
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.attribute.standard.Media
import javax.print.attribute.standard.MediaSizeName
import javax.print.attribute.standard.MediaTray


fun main() {
    // val file = File("D:\\Project\\Seer\\SeerLaProjects\\mwms\\BeiZiSuo2\\files\\tmp\\6137564B08E8036FC7978F4A-out.pdf")
    // val file = File("D:\\Project\\Seer\\SeerLaProjects\\mwms\\BeiZiSuo2\\files\\tmp\\6137595E08E8036FC7978F54-out.pdf")
    // val file = File("D:\\Project\\Seer\\mwms-server\\standard-project\\files\\tmp\\6137681A28C1C3049FEC8BCE-out.pdf")
    val file = File("D:\\Project\\Seer\\mwms-server\\standard-project\\files\\tmp\\615291888457681C05377C6D.pdf")
    
    // val printService = findPrintService("SEER-PRINTER-3-1")
    val printService = findPrintService("TOSHIBA B-EX6T3 (305 dpi)")
    
    val objs = printService!!.getSupportedAttributeValues(Media::class.java, null, null) as Array<Media>
    for (obj in objs) {
        if (obj is MediaSizeName) {
            println("纸张型号：$obj")
        } else if (obj is MediaTray) {
            println("纸张来源：$obj")
        }
    }
    
    PDDocument.load(file).use { document ->
        
        val job = PrinterJob.getPrinterJob()
        
        // job.printService = printService
        // // val p =PDPage( PDRectangle.A4)
        // // job.setPageable(PDFPageable(document, Orientation.LANDSCAPE, true))
        // // val pageFormat = PageFormat()
        // // pageFormat.paper = getPaper("A4")
        // // pageFormat.orientation = PageFormat.LANDSCAPE
        // //
        // job.setPageable(PDFPageable(document))
        // //
        // //
        // // val pdfPrintable = PDFPrintable(document, Scaling.SHRINK_TO_FIT)
        // // val pdfPageable = PDFPageable(document)
        // // pdfPageable.append(pdfPrintable, pageFormat, document.numberOfPages)
        // // job.setPageable(pdfPageable)
        //
        // val box = document.pages.first().mediaBox
        // println("width : ${box.width}")
        // println("height : ${box.height}")
        
        // -------
        // PageFormat.PORTRAIT works!
        // PageFormat.LANDSCAPE works! but it does not works on label printer
        
        val cmPerInch = 2.54
        // val width = 16 * 72 / cmPerInch * 6
        // val height = 4.6 * 72 / cmPerInch * 6
        val width = 16 * 72 / cmPerInch * 6
        val height = 4.6 * 72 / cmPerInch * 6
        val paper = Paper()
        paper.setSize(width, height)
        
        val pageFormat = PageFormat()
        // pageFormat.orientation = PageFormat.PORTRAIT
        pageFormat.paper = paper

        job.printService = printService

        val book = Book()
        val printable = PDFPrintable(document)
        book.append(printable, pageFormat)
        job.setPageable(book)

        val box = document.pages.first().mediaBox
        println("width : ${box.width}")
        println("height : ${box.height}")
        
        // -------------
        
        // 不起作用
        // val box = document.pages.first().mediaBox
        // println("width : ${box.width}")
        // println("height : ${box.height}")
        //
        // // val format = document.getPageFormat(1)
        //
        // var pageable = PDFPageable(document)
        // // val cPageable = PDFPageable(document, Orientation.LANDSCAPE)
        // val cPageable = PDFPageable(document)
        // if (box.width > box.height) {
        //     pageable = cPageable
        // }
        //
        // job.printService = printService
        // job.setPageable(pageable)

        println("---------")
        job.print()
    }
    
}

fun findPrintService(printerName: String): PrintService? {
    val printServices = PrintServiceLookup.lookupPrintServices(null, null)
    for (printService in printServices) {
        if (printService.name.trim { it <= ' ' } == printerName) {
            return printService
        }
    }
    return null
}

/**
 * 根据打印机名称判断是单据打印还是条码打印，进而创建对应Paper对象并返回
 * @param printerName
 * @return
 */
fun getPaper(printerName: String): Paper? {
    val paper = Paper()
    // 默认为A4纸张，对应像素宽和高分别为 595, 848
    var width = 841
    var height = 595
    // 设置边距，单位是像素，10mm边距，对应 28px
    val marginLeft = 0
    val marginRight = 0
    val marginTop = 0
    val marginBottom = 0
    paper.setSize(width.toDouble(), height.toDouble())
    // 下面一行代码，解决了打印内容为空的问题
    paper.setImageableArea(
        marginLeft.toDouble(),
        marginRight.toDouble(),
        (width - (marginLeft + marginRight)).toDouble(),
        (height - (marginTop + marginBottom)).toDouble()
    )
    return paper
}