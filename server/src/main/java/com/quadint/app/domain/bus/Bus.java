package com.quadint.app.domain.bus;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Bus {
    private String ROUTEID;
    private String BSTOPID;
    private String ARRIVALESTIMATETIME;

    public Bus(String ROUTEID, String BSTOPID, String ARRIVALESTIMATETIME) {
        this.ROUTEID = ROUTEID;
        this.BSTOPID = BSTOPID;
        this.ARRIVALESTIMATETIME = ARRIVALESTIMATETIME;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "ROUTEID='" + ROUTEID + '\'' +
                ", BSTOPID='" + BSTOPID + '\'' +
                ", ARRIVALESTIMATETIME='" + ARRIVALESTIMATETIME + '\'' +
                '}';
    }
}
