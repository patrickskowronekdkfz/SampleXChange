package de.samply.samplexchange.converters;

import java.util.Objects;

/**
 * Static methods for converting the bbmri.de sample types to SnomedCT codes and back.
 */
public class SnomedSamplyTypeConverter {

    private SnomedSamplyTypeConverter() {
    }

    /**
     * From SnomedCT to bbmri.de sample type.
     */
    public static String fromMiiToBbmri(String snomedType) {
        return switch (snomedType) {
            case "119297000", "122558009", "256912003" -> "whole-blood";
            case "119359002", "396997002", "396998007", "396999004", "110897001", "167913002" -> "bone-marrow";
            case "258587000", "117171008" -> "buffy-coat";
            case "119294007", "440500007", "738796001" -> "dried-whole-blood";
            case "404798000", "122551003" -> "peripheral-blood-cells-vital";
            case "119361006", "708049000", "708048008", "258958007", "446272009", "2431000181102", "2441000181109", "898205005" ->
                    "blood-plasma";
            case "119364003", "122591000" -> "blood-serum";
            case "258441009", "442039000" -> "ascites";
            case "258450006" -> "csf-liquor";
            case "119342007" -> "saliva";
            case "119339001" -> "stool-faeces";
            case "122575003" -> "urine";
            case "257261003" -> "swab";
            case "441652008" -> "tissue-ffpe";
            case "16214131000119104", "1003517007" -> "tissue-frozen";
            case "119376003" -> "tissue-other";
            case "258566005", "726740008", "18470003" -> "dna";
            case "441673008" -> "rna";
            case "33463005" -> "liquid-other";
            default -> "derivative-other";
        };
    }

    /**
     * From bbmri.de to SnomedCT sample type.
     */
    public static String fromBbmriToMii(String bbmriType) {
        String defaultSnomedcode = "123038009";
        if (Objects.equals(bbmriType, null)) {
            return defaultSnomedcode;
        }
        return switch (bbmriType) {
            case "whole-blood" -> "119297000";
            case "bone-marrow" -> "119359002";
            case "buffy-coat" -> "258587000";
            case "dried-whole-blood" -> "119294007";
            case "peripheral-blood-cells-vital" -> "404798000";
            case "blood-plasma", "plasma-cell-free", "plasma-other" -> "119361006";
            case "plasma-edta" -> "708049000";
            case "plasma-citrat" -> "708048008";
            case "plasma-heparin" -> "446272009";
            case "blood-serum" -> "119364003";
            case "ascites" -> "258441009";
            case "csf-liquor" -> "258450006";
            case "saliva" -> "119342007";
            case "stool-faeces" -> "119339001";
            case "urine" -> "122575003";
            case "swab" -> "257261003";
            case "tissue-ffpe",
                 "tumor-tissue-ffpe",
                 "normal-tissue-ffpe",
                 "other-tissue-ffpe" -> "441652008";
            case "tissue-frozen",
                 "tumor-tissue-frozen",
                 "normal-tissue-frozen",
                 "other-tissue-frozen" -> "16214131000119104";
            case "tissue-other" -> "119376003";
            case "dna" -> "258566005";
            case "cf-dna" -> "726740008";
            case "g-dna" -> "18470003"; // Check
            case "rna" -> "441673008";
            case "liquid-other" -> "33463005";
            default -> defaultSnomedcode;
        };
    }
}
