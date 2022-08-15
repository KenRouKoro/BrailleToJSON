package cn.korostudio.brailletojson.json;

import lombok.Data;

import java.util.List;

@Data
public class Bone {
    String name;
    double []pivot = {0,0,0};
    List<Cube>cubes;
}
