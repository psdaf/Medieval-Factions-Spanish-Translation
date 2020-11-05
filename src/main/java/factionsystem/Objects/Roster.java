package factionsystem.Objects;

import factionsystem.Main;

import java.util.ArrayList;
import java.util.UUID;

public class Roster {
    private Main main = null;

    public Roster(Main plugin) {
        main = plugin;
    }

    private ArrayList<UUID> members = new ArrayList<>();
    private ArrayList<UUID> officers = new ArrayList<>();
    private UUID owner = UUID.randomUUID();

    public int getNumOfficers() {
        return officers.size();
    }

    public int calculateMaxOfficers(){
        int officersPerXNumber = main.getConfig().getInt("officerPerMemberCount");
        int officersFromConfig = members.size() / officersPerXNumber;
        return 1 + officersFromConfig;
    }

    public boolean addOfficer(UUID newOfficer) {
        if (officers.size() < calculateMaxOfficers() && !officers.contains(newOfficer)){
            officers.add(newOfficer);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeOfficer(UUID officerToRemove) {
        return officers.remove(officerToRemove);
    }

    public boolean isOfficer(UUID uuid) {
        return officers.contains(uuid);
    }

    public ArrayList<UUID> getMemberArrayList() {
        return members;
    }

    public ArrayList<UUID> getMemberList() {
        return members;
    }

    public int getPopulation() {
        return members.size();
    }

    public void setOwner(UUID UUID) {
        owner = UUID;
    }

    public boolean isOwner(UUID UUID) {
        return owner.equals(UUID);
    }

    public UUID getOwner() {
        return owner;
    }
}
