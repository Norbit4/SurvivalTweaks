package pl.norbit.survivaltweaks.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MessagesConfig;
import pl.norbit.survivaltweaks.utils.*;

import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;


public class ItemSignatureCommand {
    private ItemSignatureCommand(){}

    public static void register(Commands registrar) {
        registrar.register(
                literal(ConfigManager.getConfig().getSignatureCmd())
                        .then(argument("text", StringArgumentType.greedyString())
                                .executes(ctx -> execute(
                                        ctx,
                                        StringArgumentType.getString(ctx, "text")
                                ))
                        )
                        .build(),
                "Signs the held item"
        );
    }

    private static int execute(CommandContext<CommandSourceStack> context, String signature) {
        if (!(context.getSource().getSender() instanceof Player p)) {
            return 0;
        }

        MessagesConfig messagesConfig = ConfigManager.getMessagesConfig();

        if (!PlayerUtils.hasPermission(p, messagesConfig.getItemSignaturePermission())) {
            p.sendMessage(ChatUtils.format(messagesConfig.getGlobalPermission()));
            return 0;
        }

        int cooldownTime = CooldownCommandUtils.getRemainingSeconds("item.signature", p.getUniqueId());

        if (cooldownTime > 0) {
            String cooldownMessage = messagesConfig.getGlobalCooldown()
                    .replace("{time}", String.valueOf(cooldownTime));

            p.sendMessage(ChatUtils.format(cooldownMessage));
            return 0;
        }

        PlayerInventory inv = p.getInventory();
        ItemStack itemInMainHand = inv.getItemInMainHand();

        if (itemInMainHand.isEmpty()) {
            p.sendMessage(ChatUtils.format(messagesConfig.getItemSignatureError()));
            return 0;
        }

        ItemMeta itemMeta = itemInMainHand.getItemMeta();

        if (itemMeta == null) {
            p.sendMessage(ChatUtils.format(messagesConfig.getItemSignatureError()));
            return 0;
        }

        ItemSignatureUtil.signItem(signature, itemInMainHand, p);

        p.sendMessage(ChatUtils.format(messagesConfig.getItemSignatureSuccess()));

        CooldownCommandUtils.setCooldown("item.signature", p.getUniqueId(), 5);

        return 1;
    }
}