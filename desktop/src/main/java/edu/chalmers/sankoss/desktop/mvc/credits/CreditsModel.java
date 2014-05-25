package edu.chalmers.sankoss.desktop.mvc.credits;

import edu.chalmers.sankoss.desktop.mvc.AbstractModel;

/**
 * Created by nikteg on 16/05/14.
 */
public class CreditsModel extends AbstractModel {
    private float creditsTextPosition = 0f;

    public String getCreditsText() {
        String creditsText = "Sankoss\n\nDaniel 'Eineving' Eineving\nMikael 'Laxen' Malmqvist\n" +
                "Niklas 'Bipshark' Tegnander\nFredrik 'Thune' Thune\n\nChalmers University of Technology\n\nSpring 2014";
        return creditsText;
    }

    public float getCreditsTextPosition() {
        return creditsTextPosition;
    }

    public void setCreditsTextPosition(float creditsTextPosition) {
        this.creditsTextPosition = creditsTextPosition;
        fireChange("text_position", creditsTextPosition);
    }

    @Override
    public void reset() {

    }
}
