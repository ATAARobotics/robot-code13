package edu.ata.user;

import edu.ata.auto.AutonomousMode;
import edu.ata.auto.AutonomousSelector;
import edu.ata.auto.ScriptAutonomousMode;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Singleton design pattern class that deals with the smart dashboard and
 * preferences.
 *
 * @author Joel Gallant
 */
public class UserInfo {

    // Fields:
    // #SmartDashboard
    // autoChoices = A list of the currently available autonomous modes seperated by commas (uses widget)
    // #Preferences
    // currentAuto = The current autonomous mode's name that will be run
    // scriptOn = A boolean telling the program whether or not to use the script
    // userScript = The script that the user has inputed into the script box
    private static UserInfo instance;

    /**
     * Returns the singleton instance of itself. Uses synchronized check to
     * ensure no tomfoolery.
     *
     * @return singleton instance
     */
    public static UserInfo getInstance() {
        synchronized (UserInfo.class) {
            if (instance == null) {
                instance = new UserInfo();
            }
        }
        return instance;
    }
    private final Preferences preferences = Preferences.getInstance();

    private UserInfo() {
    }

    /**
     * Updates all data - input and output.
     */
    public void updateAll() {
        updateInfo();
        updateAutonomous();
    }

    /**
     * Receives all info about robot.
     */
    public void updateInfo() {
    }

    /**
     * Sends and then receives all data pertaining to autonomous mode.
     */
    public void updateAutonomous() {
        sendAutonomous();
        getAutonomous();
    }

    private void sendAutonomous() {
        SmartDashboard.putString("autoChoices", AutonomousSelector.getInstance().toString());
    }

    private void getAutonomous() {
        if (scriptOn()) {
            // Make autonomous mode the script
            AutonomousSelector.getInstance().set(new ScriptAutonomousMode(userScript()));
        } else {
            // Make autonomous mode the current selected mode
            AutonomousSelector.getInstance().set(currentAutonomousMode());
        }
    }

    private String currentAutonomousMode() {
        if (!preferences.containsKey("currentAuto")) {
            preferences.putString("currentAuto", "DEFAULT");
        }
        return preferences.getString("currentAuto", null);
    }

    private boolean scriptOn() {
        if (!preferences.containsKey("scriptOn")) {
            preferences.putBoolean("scriptOn", true);
        }
        return preferences.getBoolean("scriptOn", userScript() != null);
    }

    private String userScript() {
        if (!preferences.containsKey("userScript")) {
            preferences.putString("userScript", "#INSERT SCRIPT HERE");
        }
        return preferences.getString("userScript", null);
    }
}