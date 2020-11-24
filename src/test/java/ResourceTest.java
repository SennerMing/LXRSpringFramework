import org.junit.jupiter.api.Test;
import org.springframework.core.io.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * @ClassName: ResourceTes
 * @Description: TODO
 * @Author: LXR
 * @DATE: 2020/11/2 16:03
 * @Version: 1.0
 */
public class ResourceTest {

    /**使用ClassPathResource获取资源**/
    @Test
    public void TestClassPath() throws IOException {
        Resource resource = new ClassPathResource("test.txt");

        //判断文件是否存在：
        if (resource.exists()) {
            System.out.println("文件存在");
        }

        //判断资源文件是否可读
        if (resource.isReadable()) {
            System.out.println("文件可读");
        }

        //判断当前Resource代表的底层资源是否已经打开
        if (resource.isOpen()) {
            System.out.println("资源文件已打开");
        }

        System.out.println(resource.getURL());//获取资源所在的URL
        System.out.println(resource.getURI());//获取资源所在的URI
        resource.getFile();//返回当前资源对应的File。
        System.out.println(resource.contentLength());//输出内容长度
        System.out.println(resource.lastModified());//返回当前Resource代表的底层资源的最后修改时间。
        resource.createRelative("MyFile");//根据资源的相对路径创建新资源。[默认不支持创建相对路径资源]
        System.out.println(resource.getFilename());//获取资源文件名
        System.out.println(resource.getDescription());


        //获取当前资源代表的输入流
        if (resource.isReadable()) {
            InputStream is = resource.getInputStream();
            System.out.println(is);
            is.close();
        }
    }

    /**使用FileSystemResource获取资源**/
    @Test
    public void TestFileSystem() throws IOException {
        Resource resource = new FileSystemResource("D:\\test.txt");
        System.out.println(resource.getFilename());
    }

    /**使用UrlResource获取资源**/
    @Test
    public void TestUrl() throws MalformedURLException {
        Resource resource = new UrlResource("http://docs.spring.io/spring/docs/4.0.0.M1/spring-framework-reference/pdf/spring-framework-reference.pdf");
        System.out.println(resource.getFilename());
    }

    /**使用ByteArrayResource获取字节数组封装的资源**/
    @Test
    public void testByteArray() throws IOException {
        ByteArrayResource resource = new ByteArrayResource("Hello".getBytes());
        System.out.println(resource.getInputStream());

    }


    /**使用InputStreamResource获取输入流封装的资源。针对于输入流的Resource，其getInputStream()方法只能被调用一次。**/
    @Test
    public void testInputStream() throws Exception {
        InputStream is = new FileInputStream("D\\test.txt");
        InputStreamResource resource = new InputStreamResource(is);
        //对于InputStreamResource而言，其getInputStream()方法只能调用一次，继续调用将抛出异常。
        InputStream is2 = resource.getInputStream();   //返回的就是构件时的那个InputStream
        System.out.println(is2);
        is.close();
    }
}
