import java.io.{File, PrintWriter}

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import org.xhtmlrenderer.pdf.ITextRenderer

import java.io.FileOutputStream




class FileModification {
  val logger = LoggerFactory.getLogger(classOf[FileModification])
  val url = ConfigFactory.load().getString("apiUrl")
  val inputFileExtension = ".md"
  val outputFileExtension = ".html"

  import scala.io.Source

  val headerContent: String = Source.fromFile(ConfigFactory.load().getString("headerPath")).mkString
  val footerContent: String = Source.fromFile(ConfigFactory.load().getString("footerPath")).mkString
  val location = ConfigFactory.load().getString("outputFileLocation")
  val fileReadObject = new MdFilehandler

  /**
    * reads list of files , converts file extension to output file extension and writes file to the location
    *
    * @return status of each file i.e. success or failure
    */
  def convertToHtml(): String = {
    val listOfFiles = readListOfFiles()
    val statusList = listOfFiles.map { file =>
      val fileURLContent = scala.io.Source.fromURL(url + file + inputFileExtension).mkString
      val getFileData = fileReadObject.getFileContent(fileURLContent)
      getFileData match {
        case Some(data: String) => val fileData = fileReadObject.ConvertMdExtension(data)
          logger.info("Begin writing [" + file + "." + outputFileExtension + "] at " + location)
          writeToFile(location + file + outputFileExtension, fileData)
          logger.info("Successfully written [" + file + "." + outputFileExtension + "] at " + location)
          "Success"
        case None => logger.error(s"$file Conversion failed ")
          "Failure"
      }
    }

    if (statusList.contains("Failure"))
      "Some Files Failed To Convert"
    else
      "All files successfully Converted"
  }

  /**
    * Reads list of files from application.conf file
    *
    * @return list of string
    */
  def readListOfFiles(): List[String] = {
    val listOfFiles = ConfigFactory.load().getStringList("fileList").asScala.toList
    logger.info(s"List of files : $listOfFiles")
    listOfFiles
  }

  /**
    * writes file to the destination provided by path parameter
    *
    * @param path storage location of the file
    * @param data contents of the file
    */
  def writeToFile(path: String, data: String): Unit = {
    val writer = new PrintWriter(new File(path))
    writer.write(headerContent + data + footerContent)
    writer.close()
  }

  def convertToPdf() ={
    var fos: FileOutputStream = null
    val File_To_Convert  ="src/main/webapp/installation-guide.html"
    val url = new File(File_To_Convert).toURI().toURL().toString()
    val HTML_TO_PDF = "src/main/webapp/installation-guide.pdf"
    fos = new FileOutputStream(HTML_TO_PDF)
    val renderer = new ITextRenderer()
    renderer.setDocument(url)
    renderer.layout()
    renderer.createPDF(fos)
    fos.close()
  }
}

