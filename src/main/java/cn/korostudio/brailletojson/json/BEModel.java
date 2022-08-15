package cn.korostudio.brailletojson.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BEModel {
    Description description ;
    List<Bone> bones = new ArrayList<>();
}
