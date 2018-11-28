package me.bactoria.celebbatch;

import me.bactoria.celebbatch.channel.Channel;
import me.bactoria.celebbatch.channelLog.ChannelLog;
import me.bactoria.celebbatch.channelLog.ChannelLogPk;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CrawlChannel {

    public final static Channel channelUpdate(Channel channel) throws IOException {
        return channelCrawl(channel);
    }

    public final static ChannelLog channelLog(Channel channel, String RequestDate, long RequestHour) throws IOException {
        Channel c = channelCrawl(channel);

        return ChannelLog.builder()
                .channelLogPk(ChannelLogPk.builder()
                        .id(c.getId())
                        .date(toLocalDate(RequestDate))
                        .hour(RequestHour)
                        .build())
                .subscriber(c.getSubscriber())
                .build();
    }

    private static LocalDate toLocalDate(String requestDate) {
        int year = Integer.valueOf(requestDate.substring(0, 4));
        int month = Integer.valueOf(requestDate.substring(4, 6));
        int day = Integer.valueOf(requestDate.substring(6));

        return LocalDate.of(year, month, day);
    }

    private final static Channel channelCrawl(Channel channel) throws IOException {
        String connUrl = "https://www.youtube.com/channel/" + channel.getId() + "/about";
        Document doc = Jsoup.connect(connUrl).get();

        Elements elements = doc.select("meta[property]");
        String title = elements.select("meta[property=og:title]").attr("content");
        String content = elements.select("meta[property=og:description]").attr("content");
        String image = elements.select("meta[property=og:image]").attr("content");

        Long subscriber = Long.valueOf(doc.select("span.subscribed").text().replace(",", ""));

        List<String> elementList = doc.select("span.about-stat").eachText();
        Long views = Long.valueOf(elementList.get(1).replaceAll("[^0-9]", ""));
        String[] joinDateArray = elementList.get(2).replaceAll("[^0-9.]", "").split("\\.");
        LocalDate joinDate = LocalDate.of(Integer.valueOf(joinDateArray[0]), Integer.valueOf(joinDateArray[1]), Integer.valueOf(joinDateArray[2]));

        LocalDateTime updatedTime = LocalDateTime.now();

        return Channel.builder()
                .id(channel.getId())
                .name(channel.getName())
                .introVideoUrl(channel.getIntroVideoUrl())
                .subscriber(subscriber)
                .title(title)
                .updatedTime(updatedTime)
                .views(views)
                .content(content)
                .image(image)
                .joinDate(joinDate)
                .build();
    }

}
