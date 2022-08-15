package cn.korostudio.brailletojson.json;

import lombok.Data;

import java.util.List;

@Data
public class Description {
    String identifier = "geometry.testID";
    double texture_width = 16;
    double texture_height = 16;
    double visible_bounds_width = 2;
    double visible_bounds_height = 1.5;
    double[] visible_bounds_offset = {0,0.25,0};
}
