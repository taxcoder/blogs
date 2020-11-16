package com.tanx.blogs;

import com.tanx.blogs.utils.OssUploadUtil;
import com.tanx.blogs.utils.ReaderPropertiesOrYamlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogsApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(ReaderPropertiesOrYamlUtil.systemEnvironment());
    }
}
