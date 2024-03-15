package tc.oc.bingo.objectives;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import tc.oc.pgm.api.match.event.MatchFinishEvent;
import tc.oc.pgm.api.player.MatchPlayer;

@Tracker("match-length")
public class MatchLengthObjective extends ObjectiveTracker {

  public static final int REQUIRED_MINS = 60;

  // TODO: make it so they have to be in a short and long match? duality of defender?

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onMatchFinish(MatchFinishEvent event) {

    Duration duration = event.getMatch().getDuration();

    if (duration.minus(REQUIRED_MINS, ChronoUnit.MINUTES).isNegative()) return;

    List<Player> players =
        event.getMatch().getParticipants().stream()
            .map(MatchPlayer::getBukkit)
            .collect(Collectors.toList());

    reward(players);
  }
}
