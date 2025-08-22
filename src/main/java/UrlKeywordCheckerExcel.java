import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UrlKeywordCheckerExcel {

    private static final String UA =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/124.0.0.0 Safari/537.36";

    public static void main(String[] args) {
        List<String> urls = Arrays.asList(
                "https://www.yesstyle.com/en/jellyfish-long-full-wig-straight/info.html/pid.1129569514",
                "https://www.yesstyle.com/en/huella-cat-phone-case-apple-iphone-16-pro-max-16-pro-16-15-pro-max/info.html/pid.1135445492",
                "https://www.yesstyle.com/en/hazie-high-rise-plaid-wide-leg-pants/info.html/pid.1131731967",
                "https://www.yesstyle.com/en/petersons-lab-ar-moisturizer-for-rosacare-30g/info.html/pid.1132328270",
                "https://www.yesstyle.com/en/milena-index-tab-sticky-notes-various-designs/info.html/pid.1134825537",
                "https://www.yesstyle.com/en/nikao-strappy-flip-flops/info.html/pid.1129183012",
                "https://www.yesstyle.com/en/poppy21-succinic-acid-anti-acne-body-wash-500ml/info.html/pid.1131077966",
                "https://www.yesstyle.com/en/koringa-animal-stainless-steel-tumbler-various-designs/info.html/pid.1132627185",
                "https://www.yesstyle.com/en/lucuna-set-round-neck-washed-button-denim-vest-high-waist-wide-leg/info.html/pid.1134980013",
                "https://www.yesstyle.com/en/apieu-deep-clean-foam-cleanser-whipping-130ml/info.html/pid.1111890166",
                "https://www.yesstyle.com/en/phone-in-the-shell-rose-plaid-phone-case-adhesive-decoration-set-iphone/info.html/pid.1131413610",
                "https://www.yesstyle.com/en/sugarcoat-sleeveless-v-neck-plain-lace-slim-fit-mini-a-line-dress/info.html/pid.1135404686",
                "https://www.yesstyle.com/en/glysomed-foot-leg-balm-100ml/info.html/pid.1122489081",
                "https://www.yesstyle.com/en/swarliss-cat-paw-nail-art-stickers-various-designs/info.html/pid.1130375774",
                "https://www.yesstyle.com/en/karnel-spaghetti-strap-plain-slit-tie-back-maxi-sheath-dress/info.html/pid.1130544194",
                "https://www.yesstyle.com/en/suncall-farjua-no-04-c-concentrate-cream-refill-500g/info.html/pid.1133138775",
                "https://www.yesstyle.com/en/san-x-sumikko-gurashi-color-change-cup-purple-h120-100-100mm/info.html/pid.1134284446",
                "https://www.yesstyle.com/en/ourea-elbow-sleeve-round-neck-cartoon-print-bow-accent-t-shirt-high/info.html/pid.1134458910",
                "https://www.yesstyle.com/en/stapi-angled-concealer-brush-black-one-size/info.html/pid.1134305645",
                "https://www.yesstyle.com/en/dibi-crew-neck-dog-embroidered-knit-tank-top/info.html/pid.1134662084",
                "https://www.yesstyle.com/en/beem-high-rise-washed-distressed-wide-leg-jeans-various-designs/info.html/pid.1134029348",
                "https://www.yesstyle.com/en/petersons-lab-bha-cleansing-foam-for-face-body-scalp-jumbo-300ml/info.html/pid.1133260086",
                "https://www.yesstyle.com/en/tanvtim-bow-collared-pet-dress/info.html/pid.1134321149",
                "https://www.yesstyle.com/en/dute-elastic-waist-plain-ruffle-mini-a-line-skirt/info.html/pid.1133468806",
                "https://www.yesstyle.com/en/kaleidos-matte-lip-mud-set-polar-placid-4-pcs/info.html/pid.1131679747",
                "https://www.yesstyle.com/en/kloudkase-heart-faux-pearl-phone-case-iphone-15-pro-max-15-pro-15/info.html/pid.1129260837",
                "https://www.yesstyle.com/en/forainer-rhinestone-star-bow-drop-earring-1-pair-silver-one-size/info.html/pid.1132654656",
                "https://www.yesstyle.com/en/eovda-faux-pearl-press-on-nails/info.html/pid.1127692902",
                "https://www.yesstyle.com/en/mobby-cat-transparent-phone-case-iphone-14-pro-max-14-pro-14-max-14/info.html/pid.1122709779",
                "https://www.yesstyle.com/en/mopagrati-short-sleeve-contrast-collar-polo-shirt/info.html/pid.1134617677",
                "https://www.yesstyle.com/en/stapi-concealer-makeup-brush-various-designs/info.html/pid.1130404614",
                "https://www.yesstyle.com/en/phone-in-the-shell-lunar-new-year-chinese-characters-phone-case-iphone/info.html/pid.1132248636",
                "https://www.yesstyle.com/en/yikes-mid-rise-plain-wide-leg-cargo-pants/info.html/pid.1133980377",
                "https://www.yesstyle.com/en/rodiv-bow-mesh-scrunchie/info.html/pid.1134982652",
                "https://www.yesstyle.com/en/sabam-set-of-2-plastic-suction-hook/info.html/pid.1132287392",
                "https://www.yesstyle.com/en/banash-mid-rise-print-washed-wide-leg-jeans/info.html/pid.1134506763",
                "https://www.yesstyle.com/en/bonnie-wisp-caramel-natural-individual-multipack-false-eyelashes/info.html/pid.1134659757",
                "https://www.yesstyle.com/en/hashi-stainless-steel-spoon-fork-knife-dessert-spoon/info.html/pid.1060372152",
                "https://www.yesstyle.com/en/taimi-floral-alloy-drop-earring-ear-cuff/info.html/pid.1134195261",
                "https://www.yesstyle.com/en/coringco-nude-skin-lip-eye-remover-100ml/info.html/pid.1092486193",
                "https://www.yesstyle.com/en/2night-embroidered-chenille-pet-top-various-designs/info.html/pid.1131088419",
                "https://www.yesstyle.com/en/dazzli-pajama-set-letter-embroidered-fleece-shirt-plain-pants/info.html/pid.1133015687",
                "https://www.yesstyle.com/en/cow-brand-soap-baby-bubble-soap-no-fragrance-moist/info.html/pid.1077801158",
                "https://www.yesstyle.com/en/tanbaya-kuroneko-design-sticker-vol-1-one-size/info.html/pid.1130391767",
                "https://www.yesstyle.com/en/mssbridal-cold-shoulder-sequin-beaded-trumpet-evening-gown/info.html/pid.1132235599",
                "https://www.yesstyle.com/en/carezone-cover-cure-anti-blemish-patch-60-patches/info.html/pid.1126809031",
                "https://www.yesstyle.com/en/rockit-rabbit-print-phone-case-iphone-16-16-pro-16-pro-max-16-plus/info.html/pid.1134798600",
                "https://www.yesstyle.com/en/konger-elbow-sleeve-plain-cutout-back-oversized-tee/info.html/pid.1134554747",
                "https://www.yesstyle.com/en/isehan-kiss-me-mommy-body-milk-citrus-200g/info.html/pid.1109671139",
                "https://www.yesstyle.com/en/aucucci-butterfly-nail-art-stickers/info.html/pid.1135368060",
                "https://www.yesstyle.com/en/kloitrinm-collared-washed-distressed-button-up-denim-jacket/info.html/pid.1125635798",
                "https://www.yesstyle.com/en/keep-in-touch-tattoo-lip-candle-tint-10-colors-205-ruby-chocolate/info.html/pid.1100740364",
                "https://www.yesstyle.com/en/jaguara-polka-dot-phone-case-samsung-flip/info.html/pid.1128455787",
                "https://www.yesstyle.com/en/beran-studio-long-sleeve-mock-neck-plain-mini-sheath-dress/info.html/pid.1126935983",
                "https://www.yesstyle.com/en/luxeflutter-fox-eye-volume-cluster-false-eyelashes/info.html/pid.1133435945",
                "https://www.yesstyle.com/en/japilavio-print-phone-case-iphone-14-pro-max-14-pro-14-max-14-13-pro/info.html/pid.1117016287",
                "https://www.yesstyle.com/en/girliepairs-high-waist-washed-wide-leg-denim-shorts/info.html/pid.1129682722",
                "https://www.yesstyle.com/en/my-scheming-bb-amino-hyaluronic-acid-2-serum-30ml/info.html/pid.1102876427",
                "https://www.yesstyle.com/en/tondephiry-leopard-print-phone-case-iphone-16-pro-max-16-pro-16-plus/info.html/pid.1133046769",
                "https://www.yesstyle.com/en/shira-short-sleeve-off-shoulder-lace-panel-mini-bodycon-dress/info.html/pid.1134411431",
                "https://www.yesstyle.com/en/laneige-perfect-renew-3x-skin-refiner-mini-30ml/info.html/pid.1135265232",
                "https://www.yesstyle.com/en/elliure-animal-cartoon-magnetic-compatible-with-magsafe-phone-case/info.html/pid.1134932085",
                "https://www.yesstyle.com/en/livsia-beaded-necklace-x1433-gold-one-size/info.html/pid.1132771723",
                "https://www.yesstyle.com/en/ishizawa-lab-trendholic-mazepoo-shampoo-add-on-color-purple-2g-x-10/info.html/pid.1133248944",
                "https://www.yesstyle.com/en/yulu-pineapple-reindeer-garment-organizer-various-designs/info.html/pid.1131177453",
                "https://www.yesstyle.com/en/ragistiq-high-waist-washed-denim-wide-leg-shorts/info.html/pid.1130736755",
                "https://www.yesstyle.com/en/kochanie-stainless-steel-cuticle-nippers/info.html/pid.1131054201",
                "https://www.yesstyle.com/en/phone-in-the-shell-lettering-applique-melange-phone-case-iphone-16/info.html/pid.1132408948",
                "https://www.yesstyle.com/en/okwano-short-sleeve-plain-polo-shirt/info.html/pid.1129865124",
                "https://www.yesstyle.com/en/klandail-star-press-on-nail/info.html/pid.1125762350",
                "https://www.yesstyle.com/en/galeon-glitter-floral-phone-case-apple-iphone-16-pro-max-16-pro-16/info.html/pid.1134124038",
                "https://www.yesstyle.com/en/frigga-long-sleeve-crew-neck-gingham-button-up-blouse-yellow-one-size/info.html/pid.1129613155",
                "https://www.yesstyle.com/en/jasmintinea-long-full-wig-wavy-gradient/info.html/pid.1129629230",
                "https://www.yesstyle.com/en/mobby-dog-applique-plaid-phone-case-beaded-phone-strap-set/info.html/pid.1134476475",
                "https://www.yesstyle.com/en/tzarkozy-notch-lapel-plain-tag-single-breasted-blazer/info.html/pid.1119938686",
                "https://www.yesstyle.com/en/parnell-brightening-ampoule-toner-200ml/info.html/pid.1132863707",
                "https://www.yesstyle.com/en/jimo-watercolor-frame-sticker-various-designs/info.html/pid.1134820199",
                "https://www.yesstyle.com/en/gagao-off-shoulder-plain-crop-sweatshirt/info.html/pid.1133338502",
                "https://www.yesstyle.com/en/kokoa-sanrio-my-melody-hair-roller-4pcs-pink-one-size/info.html/pid.1135314344",
                "https://www.yesstyle.com/en/ikiana-floral-pencil-case-pink-one-size/info.html/pid.1130763643",
                "https://www.yesstyle.com/en/elbelier-short-sleeve-cold-shoulder-plain-cutout-ruffle-trim-mini/info.html/pid.1134381946",
                "https://www.yesstyle.com/en/jmsolution-skin-boost-glutathione-micellar-cleansing-water-1-5-500ml/info.html/pid.1133934176",
                "https://www.yesstyle.com/en/likamika-lunar-new-year-chinese-characters-gold-leaf-phone-case-iphone/info.html/pid.1132674323",
                "https://www.yesstyle.com/en/faeris-mock-two-piece-short-sleeve-crewneck-plaid-panel-t-shirt/info.html/pid.1133952364",
                "https://www.yesstyle.com/en/handaiyan-6-color-pearlescent-matte-diamond-liquid-lipstick-set-2/info.html/pid.1131772225",
                "https://www.yesstyle.com/en/cissilli-cartoon-hooded-padded-pet-overall-various-designs/info.html/pid.1132892906",
                "https://www.yesstyle.com/en/femmal-long-sleeve-v-neck-plain-ruched-ruffle-trim-mesh-slim-fit-top/info.html/pid.1134295506",
                "https://www.yesstyle.com/en/nuxe-reve-de-miel-honey-lip-balm-15g/info.html/pid.1120659033",
                "https://www.yesstyle.com/en/bloomsoon-genshin-impact-lan-yan-cosplay-costume-set-wig-shoes/info.html/pid.1134022150",
                "https://www.yesstyle.com/en/femme-cradle-set-off-shoulder-plain-ruched-open-back-mesh-slit-maxi/info.html/pid.1134970491",
                "https://www.yesstyle.com/en/tuhat-hair-claw-various-designs/info.html/pid.1130938849",
                "https://www.yesstyle.com/en/jaguara-floral-bead-strap-phone-case-samsung/info.html/pid.1132419802",
                "https://www.yesstyle.com/en/sheck-elbow-sleeve-collared-knot-button-up-shirt/info.html/pid.1135105103",
                "https://www.yesstyle.com/en/brain-cosmos-ipo-car-ex-gel-patch-for-wart-skin-25g/info.html/pid.1134886049",
                "https://www.yesstyle.com/en/ekapop-lettering-kraft-paper-thank-you-greeting-card-various-designs/info.html/pid.1133991503",
                "https://www.yesstyle.com/en/lady-jean-high-rise-plain-midi-a-line-pleated-skirt/info.html/pid.1133248243",
                "https://www.yesstyle.com/en/florasis-ginseng-care-lip-balm-lip-balm-6g/info.html/pid.1135250484",
                "https://www.yesstyle.com/en/sanrio-sanrio-photo-card-holder-keyring-pochacco-9-5-x-1-x-14cm/info.html/pid.1129720462",
                "https://www.yesstyle.com/en/cribi-elbow-sleeve-planet-embroidered-t-shirt/info.html/pid.1109938602",
                "https://www.yesstyle.com/en/shobido-decorative-nail-scrub-15g/info.html/pid.1118692907",
                "https://www.yesstyle.com/en/harnai-dotted-bow-accent-phone-case-iphone-16-pro-max-16-pro-16-plus/info.html/pid.1133650489",
                "https://www.yesstyle.com/en/leffa-short-sleeve-collared-bow-sequin-beaded-mesh-midi-a-line-cocktail/info.html/pid.1123340303",
                "https://www.yesstyle.com/en/cosplus-julia-uv-nail-base-gel-15g/info.html/pid.1112776944",
                "https://www.yesstyle.com/en/candyiry-christmas-lingerie-costume-set-set-dress-1-pair-wrist-cuffs/info.html/pid.1131946648",
                "https://www.yesstyle.com/en/dute-collared-faux-leather-jacket-v-neck-cardigan-plaid-mini-skirt/info.html/pid.1131510408",
                "https://www.yesstyle.com/en/numbuzin-no-3-pore-makeup-cleansing-balm-with-green-tea-and-charcoal/info.html/pid.1122218010",
                "https://www.yesstyle.com/en/marushin-my-neighbor-totoro-hand-towel-totoros-stomach-25-x-25cm/info.html/pid.1134770218",
                "https://www.yesstyle.com/en/hanji-spaghetti-strap-v-neck-plain-tie-up-ruched-panel-mini-a-line/info.html/pid.1134450814",
                "https://www.yesstyle.com/en/kenei-pharm-vaseline-lip-10g/info.html/pid.1126661435",
                "https://www.yesstyle.com/en/elliure-animal-embroidered-denim-phone-case-iphone-16-pro-max-16-pro/info.html/pid.1131519791",
                "https://www.yesstyle.com/en/tuhat-animal-fabric-makeup-bag-various-designs/info.html/pid.1131736071",
                "https://www.yesstyle.com/en/deminsha-fork-spoon-faux-pearl-hair-claw/info.html/pid.1132500278",
                "https://www.yesstyle.com/en/buttermilk-cartoon-waterproof-and-oil-proof-mouse-pad/info.html/pid.1127213566",
                "https://www.yesstyle.com/en/yuxi-halter-plain-open-back-accordion-pleated-maxi-a-line-dress/info.html/pid.1134378544",
                "https://www.yesstyle.com/en/yanyus-lettering-head-band/info.html/pid.1129034466",
                "https://www.yesstyle.com/en/tondephiry-floral-applique-phone-case-with-back-pocket-strap-for-iphone/info.html/pid.1123996782",
                "https://www.yesstyle.com/en/mulgam-lettering-zip-crossbody-bag/info.html/pid.1135105271",
                "https://www.yesstyle.com/en/hacci-honey-lotion-150ml/info.html/pid.1058158777",
                "https://www.yesstyle.com/en/blooming-daisies-star-phone-case-apple-iphone-16-pro-max-16-pro-16/info.html/pid.1134475983",
                "https://www.yesstyle.com/en/citybae-distressed-denim-shorts/info.html/pid.1134760138",
                "https://www.yesstyle.com/en/miche-bloomin-mascara-glue-remover-10ml/info.html/pid.1129680538",
                "https://www.yesstyle.com/en/cosmocorner-traditional-chinese-short-sleeve-ribbon-dress/info.html/pid.1104825525",
                "https://www.yesstyle.com/en/metonymph-spaghetti-strap-v-neck-flower-print-mini-bodycon-dress-v/info.html/pid.1134013854",
                "https://www.yesstyle.com/en/to-one-pore-care-jelly-mask-45g/info.html/pid.1129002109",
                "https://www.yesstyle.com/en/iswas-cherry-trim-food-container-s-hot-pink-one-size/info.html/pid.1094636168",
                "https://www.yesstyle.com/en/sensarra-long-sleeve-collared-button-down-denim-shirt/info.html/pid.1132611263",
                "https://www.yesstyle.com/en/cloffice-bow-headband/info.html/pid.1107114980",
                "https://www.yesstyle.com/en/edgin-cat-airpods-pro-earphone-case-skin/info.html/pid.1133978393",
                "https://www.yesstyle.com/en/seoulpop-short-sleeve-lettering-loose-fit-tee-black-one-size/info.html/pid.1134679057",
                "https://www.yesstyle.com/en/eovda-print-faux-nail-tips/info.html/pid.1121086167",
                "https://www.yesstyle.com/en/caraboomie-set-of-2-fish-cat-kitchen-suction-cleaning-sponge-refill/info.html/pid.1135106695",
                "https://www.yesstyle.com/en/lydiah-spaghetti-strap-scoop-neck-sequin-maxi-bodycon-dress/info.html/pid.1133479503",
                "https://www.yesstyle.com/en/formotopia-bffect-c-002-tranexamic-acid-2-whitening-serum-30ml/info.html/pid.1125868789",
                "https://www.yesstyle.com/en/elliure-bear-heart-print-phone-case-iphone-16-pro-max-16-pro-16-plus/info.html/pid.1133290171",
                "https://www.yesstyle.com/en/destine-short-sleeve-sequin-mesh-a-line-evening-gown-midi-dress/info.html/pid.1132107673",
                "https://www.yesstyle.com/en/beauty-artisan-makeup-brush-various-designs-set/info.html/pid.1127751872",
                "https://www.yesstyle.com/en/dainlili-feather-rhinestone-nail-art-stickers-t300-white-one-size/info.html/pid.1135003764",
                "https://www.yesstyle.com/en/moon-city-wide-leg-jeans/info.html/pid.1113868144",
                "https://www.yesstyle.com/en/fiancee-point-hair-stick-hold-pure-shampoo-pure-shampoo-10ml/info.html/pid.1133003800",
                "https://www.yesstyle.com/en/tondephiry-faux-leather-phone-case-iphone-16-pro-max-16-pro-16-plus/info.html/pid.1131413982",
                "https://www.yesstyle.com/en/denimot-low-rise-denim-hot-pants/info.html/pid.1122842076",
                "https://www.yesstyle.com/en/jumiso-snail-mucin-88-peptide-facial-cream-100ml/info.html/pid.1128580321",
                "https://www.yesstyle.com/en/rockit-transparent-textured-ipad-case/info.html/pid.1119402636",
                "https://www.yesstyle.com/en/pandago-two-tone-hooded-puffer-jacket/info.html/pid.1127269376",
                "https://www.yesstyle.com/en/demi-hair-seasons-calmly-wash-shampoo-250ml/info.html/pid.1061744012",
                "https://www.yesstyle.com/en/orungeetun-stand-collar-plain-hood-fluffy-trim-midi-toggle-puffer/info.html/pid.1132410900",
                "https://www.yesstyle.com/en/whoosh-long-sleeve-plain-lace-panel-mock-two-piece-tee/info.html/pid.1133005625",
                "https://www.yesstyle.com/en/deminsha-mermaid-tail-hair-stick/info.html/pid.1133244524",
                "https://www.yesstyle.com/en/swarliss-lace-flower-nail-art-stickers/info.html/pid.1131072887",
                "https://www.yesstyle.com/en/sheck-long-sleeve-plain-shirt/info.html/pid.1131492861"
                );

        List<String> keywords = Arrays.asList("Major Ingredients");

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Results");

        // header
        Row h = sheet.createRow(0);
        h.createCell(0).setCellValue("URL");
        h.createCell(1).setCellValue("Status"); // HTTP status or error reason
        for (int i = 0; i < keywords.size(); i++) {
            h.createCell(i + 2).setCellValue(keywords.get(i));
        }

        int rowIdx = 1;
        for (String url : urls) {
            Row r = sheet.createRow(rowIdx++);
            r.createCell(0).setCellValue(url);

            try {
                // polite delay to avoid rate limiting
                Thread.sleep(800);

                Connection.Response resp = Jsoup.connect(url)
                        .userAgent(UA)
                        .referrer("https://www.google.com/")
                        .timeout(15000)
                        .followRedirects(true)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true) // don't throw on 403/404
                        .execute();

                int code = resp.statusCode();
                r.createCell(1).setCellValue(code); // write HTTP code

                if (code == 200) {
                    Document doc = resp.parse();
                    String text = doc.text().toLowerCase(Locale.ROOT);

                    for (int i = 0; i < keywords.size(); i++) {
                        String k = keywords.get(i).toLowerCase(Locale.ROOT);
                        r.createCell(i + 2).setCellValue(text.contains(k) ? "YES" : "NO");
                    }
                } else {
                    // mark as NO for all keywords when not 200
                    for (int i = 0; i < keywords.size(); i++) {
                        r.createCell(i + 2).setCellValue("NO");
                    }
                }

            } catch (Exception e) {
                r.createCell(1).setCellValue("ERROR: " + e.getClass().getSimpleName());
                for (int i = 0; i < keywords.size(); i++) {
                    r.createCell(i + 2).setCellValue("NO");
                }
            }
        }

        for (int i = 0; i < keywords.size() + 2; i++) sheet.autoSizeColumn(i);

        try (FileOutputStream out = new FileOutputStream("UrlKeywordResults.xlsx")) {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try { wb.close(); } catch (IOException ignored) {}
        System.out.println("Done. Check UrlKeywordResults.xlsx");
    }
}