package entities;

import world.PlaySpace;

public class TeamComp {

        public static Player[] createLeftTeam(PlaySpace playSpace) {

                return new Player[] {
                                new Goalkeeper(
                                                1,
                                                playSpace.getLength() / 12,
                                                playSpace.getWidth() / 2,
                                                0.0),

                                new Defender(
                                                2,
                                                playSpace.getLength() / 5,
                                                playSpace.getWidth() / 3,
                                                0.0),

                                new Defender(
                                                3,
                                                playSpace.getLength() / 5,
                                                (playSpace.getWidth() * 2) / 3,
                                                0.0),

                                new Midfielder(
                                                4,
                                                (playSpace.getLength() * 2) / 5,
                                                playSpace.getWidth() / 5,
                                                0.0),

                                new Midfielder(
                                                5,
                                                (playSpace.getLength() * 2) / 5,
                                                (playSpace.getWidth() * 4) / 5,
                                                0.0),

                                new Attacker(
                                                6,
                                                (playSpace.getLength() * 3) / 5,
                                                playSpace.getWidth() / 2,
                                                0.0)

                };

        }

        public static Player[] createRightTeam(PlaySpace playSpace) {

                return new Player[] {
                                new Goalkeeper(
                                                1,
                                                (playSpace.getLength() * 11) / 12,
                                                playSpace.getWidth() / 2,
                                                0.0),

                                new Defender(
                                                2,
                                                (playSpace.getLength() * 4) / 5,
                                                playSpace.getWidth() / 3,
                                                0.0,
                                                400.0,
                                                250),

                                new Defender(
                                                3,
                                                (playSpace.getLength() * 4) / 5,
                                                (playSpace.getWidth() * 2) / 3,
                                                0.0),

                                new Midfielder(
                                                4,
                                                (playSpace.getLength() * 3) / 5,
                                                playSpace.getWidth() / 5,
                                                0.0),

                                new Midfielder(
                                                5,
                                                (playSpace.getLength() * 3) / 5,
                                                (playSpace.getWidth() * 4) / 5,
                                                0.0),

                                new Attacker(
                                                6,
                                                (playSpace.getLength() * 2) / 5,
                                                playSpace.getWidth() / 2,
                                                0.0)

                };

        }

}
