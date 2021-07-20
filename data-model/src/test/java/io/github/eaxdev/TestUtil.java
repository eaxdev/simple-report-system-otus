package io.github.eaxdev;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtil {

    public static String readFileAsString(final String filePath) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

}
