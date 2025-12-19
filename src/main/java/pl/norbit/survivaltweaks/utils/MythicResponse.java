package pl.norbit.survivaltweaks.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MythicResponse {
    private MythicResponseType type;
    private String name;
}
