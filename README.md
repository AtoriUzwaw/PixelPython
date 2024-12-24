# PixelPython

PixelPython 是一个基于 JavaFX 的贪吃蛇项目。本项目实现了经典的贪吃蛇游戏，用户可以控制蛇的移动，并与食物互动，逐渐增长长度并获得更高分数。

## 项目背景

PixelPython 项目使用 JavaFX 实现，旨在帮助我熟悉 JavaFX 库及其应用。作为我第一次使用 JavaFX 完成的项目，写得并不完美，但通过这个项目，我深入理解了 JavaFX 的布局管理、事件处理和图形渲染等方面的内容。项目也通过一些简单的动画和事件处理机制，帮助我进一步掌握了如何构建基于图形界面的桌面应用程序。

## 技术栈

- **Java 17**：后端逻辑和核心功能的实现。
- **JavaFX 21**：图形界面的实现，提供丰富的 UI 控件和动画效果。
- **Maven**：构建和管理项目依赖，简化了项目的构建和依赖管理。
- **SQLite**：为了便于部署与使用，选用 SQLite 数据库用于数据存储，保存用户的游戏分数和其他信息。
- **Spring**：提供依赖注入、事务管理等功能，帮助简化项目的开发和扩展。
- **MyBatis**：用于数据库操作的持久层框架，简化了 SQL 语句与 Java 对象的映射。

## 项目功能

- **贪吃蛇游戏**：用户控制蛇的移动，吃掉食物并增长长度。
- **得分系统**：每吃掉一块食物，得分增加。
- **游戏结束判定**：当蛇碰到墙壁或自己时，游戏结束。
- **数据库支持**：记录玩家的历史记录，保存在 SQLite 数据库中。

## 项目结构

PixelPython/<br>
├── src/<br>
│   ├── main/<br>
│   │   ├── java/<br>
│   │   │   ├── com/<br>
│   │   │   │   ├── atri/<br>
│   │   │   │   │   ├── config/          # Spring 与数据库配置<br>
│   │   │   │   │   ├── controller/      # 控制器层<br>
│   │   │   │   │   ├── dao/             # 数据库操作层<br>
│   │   │   │   │   ├── entity/          # 实体类<br>
│   │   │   │   │   ├── scene/           # 界面布局和交互<br>
│   │   │   │   │   ├── service/         # 服务层<br>
│   │   │   │   │   ├── sprite/          # 精灵类和图形组件<br>
│   │   │   │   │   ├── util/            # 工具类<br>
│   │   │   │   │   ├── view/            # 视图层<br>
│   │   │   │   │   ├── Main.java        # 启动类<br>
│   └── resources/<br>
│       ├── css/                         # 样式文件<br>
│       ├── font/                        # 字体文件<br>
│       ├── fxml/                        # FXML 布局文件<br>
│       ├── image/                       # 图片资源<br>
│       ├── sound/                       # 音效文件<br>
│       ├── pixel_python.db              # 数据库文件<br>
├── .gitignore                           # Git 忽略文件配置<br>
├── CONTRIBUTING.md                      # 贡献指南<br>
├── dependency-reduced-pom.xml           # Maven构建时生成的精简依赖文件<br>
├── LICENSE                              # 项目许可证文件<br>
├── pom.xml                              # Maven构建配置文件<br>
└── README.md                            # 项目说明文件<br>

## 安装与运行

### 前提条件

确保你已安装：

- Java 17 或更高版本
- Maven：用于构建和管理依赖
- JavaFX：项目使用 JavaFX 进行界面设计，JavaFX 需要通过外部库配置
- SQLite：用于存储应用数据

### 1. 克隆项目

```bash
git clone https://github.com/AtoriUzwaw/PixelPython.git
cd PixelPython
```

### 2. 构建项目

使用 Maven 构建项目

### 3. 运行项目

#### 方法一：通过命令行运行 JAR 文件

1. 确保已正确下载并配置了 JavaFX 运行时库。
2. 运行以下命令启动项目：

   ```bash
   java --module-path "path_to_javafx_libs" --add-modules javafx.controls,javafx.fxml,javafx.media --add-opens javafx.base/com.sun.javafx=ALL-UNNAMED -jar "path_to_PixelPython-1.0-SNAPSHOT.jar"
   ```

   其中，`path_to_javafx_libs` 为本地 JavaFX 库的路径，`path_to_PixelPython-1.0-SNAPSHOT.jar` 为 jar 包路径。

#### 方法二：通过 IDE / maven 运行源码

1. 配置好本地 JavaFX 依赖运行后，也可以直接在 IDE 中运行 `com.atri.Main` 类。

2. 或通过 maven 运行（相关依赖已导入）
   ```bash
   mvn clean javafx:run
   ```

### 4. 项目中使用的数据库

项目使用 SQLite 历史记录数据。数据库已创建完毕，源文件存放在 `resource` 目录下。运行项目后会将数据库文件复制到运行目录下的 `db` 目录下。

## 常见问题

1. **无法启动应用程序**  
   如果遇到 "缺少 JavaFX 运行时组件" 的错误，确保已正确配置 JavaFX 模块路径。

2. **JavaFX 运行时路径问题**  
   如果 JavaFX 组件没有正确加载，确保在运行命令时通过 `--module-path` 指定了正确的 JavaFX 运行时路径，并添加了所需的 JavaFX 模块，如 `javafx.controls` 和 `javafx.fxml`。

## 开发者

本项目由 [AtoriUzwaw](https://github.com/AtoriUzwaw) 开发，欢迎贡献代码。

## 许可证

该项目采用 MIT 许可证，详情请见 LICENSE 文件。
