package cn.edu.gdmec.s07150808.musicplayer.lyric;

import java.util.List;


public interface ILrcBuilder {
    List<LrcRow> getLrcRows(String rawLrc);
}
