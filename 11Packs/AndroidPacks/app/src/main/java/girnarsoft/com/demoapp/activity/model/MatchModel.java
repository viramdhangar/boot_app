package girnarsoft.com.demoapp.activity.model;

import android.databinding.BaseObservable;

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

    public String getShortType() {
        return shortType;
    }

    public void setShortType(String shortType) {
        this.shortType = shortType;
    }

    //    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
//    public static void setImageUrl(ImageView imageView, Object imageUrl, Drawable placeHolder) {
//        if (placeHolder == null) {
//            ImageLoader.loadImage(imageView.getContext(), imageUrl, imageView);
//        } else {
//            ImageLoader.loadImage(imageView.getContext(), imageUrl, imageView, placeHolder);
//        }
//    }
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
