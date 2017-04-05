import org.mockito.Mockito
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar


class MdFileHandlerTest extends FlatSpec with MockitoSugar {

  val mockedObj = Mockito.spy(new MdFilehandler)

  "getFileContents" should "return contents of file" in {

    Mockito.when(mockedObj.getFileContent("fakeURL")).thenReturn(Some("fileContents"))
    assert(mockedObj.getFileContent("fakeURL") === Some("fileContents"))

  }

  "convertMdExtension" should "return contents of file after required modifications" in {

    Mockito.when(mockedObj.ConvertMdExtension("fileData")).thenReturn("modifiedFileData")
    assert(mockedObj.ConvertMdExtension("fileData") === "modifiedFileData")
  }
}


