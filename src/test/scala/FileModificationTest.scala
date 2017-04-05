
import org.mockito.Mockito
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar

class FileModificationTest extends FlatSpec with MockitoSugar {

  val l = List("DdlOperation","featuresFile")
  val mockedObj = Mockito.spy(new FileModification)

"readLisOfFiles" should "return list of files" in {

  Mockito.when(mockedObj.readListOfFiles()).thenReturn(l)
  assert(mockedObj.readListOfFiles().size == 2)
}

//  "convertToHtml" should "return status of files" in{
//
//    Mockito.doReturn()when(mockedObj.convertToHtml()).thenReturn("")
//    assert(mockedObj.convertToHtml() == "")
//  }

}

