# MagicCore — Plugin lõi hệ thống phép thuật cho Aetheria

Plugin Paper 1.21.x quản lý: Mana, Stamina, Magic Level/XP, Spell, House, Class.

## Tính năng đã có (MVP)

- **Mana / Stamina**: tự hồi mỗi giây, hiển thị action bar (❤ HP · 🔮 Mana · ⚡ Stamina · ✨ Magic Level).
- **Magic Level & XP**: cast phép thành công nhận XP, lên level tự động tăng Max Mana.
- **7 Spell mẫu** theo đúng tài liệu thiết kế: Ember, Lumina, Minor Heal (Lv.1), Frostbind, Wind Dash (Lv.10), Thunderfall (Lv.30), Astral Rift — ultimate (Lv.50). Có mana cost, cooldown, range, element, level requirement.
- **4 House** (Ignis/Noctis/Silva/Aether) với GUI chọn Nhà, hệ thống House Points lưu riêng (`houses.yml`), bảng xếp hạng `/house top`.
- **6 Class** (Arcanist/Spellblade/Druid/Alchemist/Warlock/Guardian) với GUI chọn Class.
- Dữ liệu người chơi lưu YAML riêng từng file trong `playerdata/`, tự load khi join, tự save khi quit/tắt server.
- Lệnh admin `/magicadmin` để cộng/set điểm House, set level, set mana cho GM/event.

## Lệnh

| Lệnh | Mô tả |
|---|---|
| `/magic` | Xem hồ sơ phép thuật (HP/Mana/Stamina/Level/House/Class) |
| `/house info` `/house join` `/house top` | Xem, gia nhập, bảng xếp hạng Nhà |
| `/class info` `/class choose` | Xem, chọn Class |
| `/spell list` | Xem danh sách phép đã/chưa mở khoá |
| `/spell cast <id>` | Dùng phép, vd `/spell cast ember` |
| `/magicadmin housepoints <house> <add\|set> <amount>` | (OP) Sửa điểm House |
| `/magicadmin setlevel <player> <level>` | (OP) Sửa Magic Level |
| `/magicadmin setmana <player> <amount>` | (OP) Sửa Mana |

## Build

Cần **JDK 21** và **Maven** (máy dev của bạn cần có internet để Maven tải `paper-api` từ repo PaperMC — môi trường mình soạn code này không có mạng nên **chưa build/test thực tế được**, bạn cần tự `mvn package` ở máy có mạng trước khi dùng).

```bash
mvn clean package
```

File `.jar` sẽ nằm ở `target/MagicCore.jar`. Copy vào thư mục `plugins/` của server (theo ảnh bạn gửi là thư mục `plugins` trên Hypercore panel), restart server.

## Cấu hình

Sau lần chạy đầu, plugin tạo `plugins/MagicCore/config.yml` — chỉnh mana regen, XP curve, v.v. tại đây mà không cần sửa code.

## Việc cần làm tiếp (gợi ý theo roadmap XXV/XXVI trong tài liệu thiết kế)

1. **Learn-before-cast**: hiện tại bất kỳ ai đủ Magic Level đều cast được spell luôn; nếu muốn đúng flow "Học → luyện → thành thạo" cần thêm bước học phép ở NPC/Trainer (Citizens) trước khi mở khoá.
2. **Tích hợp House vào LuckPerms**: gán prefix/permission group theo House khi join, để phân biệt trong chat/tab list.
3. **Wand System**: hiện spell cast qua lệnh `/spell cast`; nên gắn vào right-click đũa phép (MMOItems) để đúng trải nghiệm RPG.
4. **Boss & Dungeon**: kết hợp MythicMobs — Boss có thể gọi các Spell class này làm attack pattern.
5. **BetonQuest**: có thể gọi `/magicadmin housepoints` và check magic-level qua Variable/Condition custom để tích hợp quest.
