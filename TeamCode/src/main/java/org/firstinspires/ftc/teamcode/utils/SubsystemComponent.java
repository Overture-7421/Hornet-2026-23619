package org.firstinspires.ftc.teamcode.utils;

import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.components.Component;
import dev.nextftc.core.subsystems.Subsystem;

import java.util.HashSet;
import java.util.Set;

/**
 * This component adds Subsystems to your OpMode
 */
public class SubsystemComponent implements Component {

    private final Set<Subsystem> subsystems = new HashSet<>();

    public SubsystemComponent(Subsystem... subsystems) {
        // Kotlin version: subsystems.flatMap { it.subsystems }.toSet()
        for (Subsystem subsystem : subsystems) {
            Set<Subsystem> inner = subsystem.getSubsystems(); // assumes .subsystems becomes getter
            this.subsystems.addAll(inner);
        }
    }

    @Override
    public void preInit() {
        for (Subsystem subsystem : subsystems) {
            subsystem.initialize();
        }
    }

    @Override
    public void preUpdate() {
        updateSubsystems();
    }

    private void updateSubsystems() {
        for (Subsystem subsystem : subsystems) {
            subsystem.periodic();
            if (!CommandManager.INSTANCE.hasCommandsUsing(subsystem)) {
                subsystem.getDefaultCommand().schedule();
            }
        }
    }
}
