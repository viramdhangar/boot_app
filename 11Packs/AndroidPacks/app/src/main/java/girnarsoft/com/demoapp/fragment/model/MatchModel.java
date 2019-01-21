package girnarsoft.com.demoapp.fragment.model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import girnarsoft.com.demoapp.utils.imageloader.ImageLoader;

public class MatchModel extends BaseObservable {
    private String imageUrl;
    private String time;
    private String teamOne;
    private String teamtwo;
    private boolean squad;
    private boolean matchStarted;
    private String type;
    private String seriesName;
    private String shortName;
    private String shortType;
    private String teamOneUrl;
    private String teamTwoUrl;
    private long timeRemaining;
    private String uniqueId;
    private String isActive;


    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public String getShortType() {
        return shortType;
    }

    public void setShortType(String shortType) {
        this.shortType = shortType;
    }

    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, Object imageUrl, Drawable placeHolder) {
        if (placeHolder == null) {
            ImageLoader.loadImage(imageView.getContext(), imageUrl, imageView);
        } else {
            ImageLoader.loadImage(imageView.getContext(), imageUrl, imageView, placeHolder);
        }
    }

    public String getTeamOneUrl() {
        return teamOneUrl;
    }

    public void setTeamOneUrl(String teamOneUrl) {
        this.teamOneUrl = teamOneUrl;
    }

    public String getTeamTwoUrl() {
        return teamTwoUrl;
    }

    public void setTeamTwoUrl(String teamTwoUrl) {
        this.teamTwoUrl = teamTwoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(String teamOne) {
        this.teamOne = teamOne;
    }

    public String getTeamtwo() {
        return teamtwo;
    }

    public void setTeamtwo(String teamtwo) {
        this.teamtwo = teamtwo;
    }

    public boolean isSquad() {
        return squad;
    }

    public void setSquad(boolean squad) {
        this.squad = squad;
    }

    public boolean isMatchStarted() {
        return matchStarted;
    }

    public void setMatchStarted(boolean matchStarted) {
        this.matchStarted = matchStarted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
