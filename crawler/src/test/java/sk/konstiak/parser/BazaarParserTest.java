package sk.konstiak.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sk.konstiak.model.Advertisement;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BazaarParserTest {

    @Autowired
    private ModryKonikParser modryKonikParser;

    @Test
    void parse() {
        File inputFile = new File(BazaarParserTest.class.getResource("modryKonik.html").getFile());
        List<Advertisement> advertisements = modryKonikParser.parse(inputFile);
        assertTrue(!advertisements.isEmpty());
    }
}