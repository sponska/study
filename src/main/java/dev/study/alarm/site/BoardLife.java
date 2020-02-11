package dev.study.alarm.site;

import dev.study.alarm.document.SiteDocument;
import dev.study.alarm.notifier.Notifier;
import lombok.RequiredArgsConstructor;
import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BoardLife implements Site {

    private final Notifier notifier;

    public void notifyNewItem(SiteDocument siteDocument) throws IOException, JSONException {
        if (siteDocument.isNewItem()) {
            String text = siteDocument.getText();
            notifier.notify(text);
        }
    }
}
