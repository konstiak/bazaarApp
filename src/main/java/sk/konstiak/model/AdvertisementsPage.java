package sk.konstiak.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "advertisementsPage")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class AdvertisementsPage {

    @XmlElement(name = "advertisement")
    private List<Advertisement> advertisements;

}
