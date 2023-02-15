package com.tricolorfire.labfx.control;

import com.tricolorfire.labfx.LayoutBinder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.WritableValue;
import javafx.css.*;
import javafx.css.converter.SizeConverter;
import javafx.css.converter.URLConverter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>实验室软件风格按钮</p>
 * 增加的 css 配置 :
 * <ul>
 * 	   <li>-fx-image : 图片 </li>
 *     <li>-fx-image-width : 图片宽</li>
 *     <li>-fx-image-height : 图片高</li>
 * </ul>
 * <p> 其中 -fx-image 的用法为输入 URL 地址以获取图片 :</p>
 * <pre><code>
 *    LabButton labButton = new LabButton();
 *    labButton.setStyle("-fx-image:url(\"test.png\")");
 * </code></pre>
 */
public class LabButton extends TButton{
	
    public static final double DEFAULT_IMAGE_WIDTH = 28.0;
    public static final double DEFAULT_IMAGE_HEIGHT = 28.0;
    
    private ObjectProperty<Image> imageProperty;  // 图片
    private StringProperty imageUrlProperty;      // 图片地址
	private DoubleProperty imageHeightProperty;   // 图片高度
	private DoubleProperty imageWidthProperty;    // 图片宽度
	
	@FXML private ImageView innerImageView;       // 展示图片的容器

	public LabButton() {
		super();
		bindFXML(null);
	}
	
	public LabButton(Image image,String title) {
		super(title);
		bindFXML(image);
	}
	
	public LabButton(String imagePath,String title) {
		super(title);
		// 初始化图片
		bindFXML(new Image(imagePath));
	}
	
    /***************************************************************************
     *                                                                         *
     *                         初始化:绑定布局                           								*
     *                                                                         *
     **************************************************************************/
	private void bindFXML(Image image) {
		
        try {
			LayoutBinder.bind(LabButton.class,"labButton/lab-button.fxml", this, this).load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    	// 绑定图片
    	innerImageView.imageProperty().bindBidirectional(imageProperty());
    	innerImageView.fitWidthProperty().bind(imageWidthProperty());
    	innerImageView.fitHeightProperty().bind(imageHeightProperty());
    	
    	// 设置图片内容
    	imageProperty().set(image);
    	
	}

    /***************************************************************************
     *                                                                         *
     *                         增加CSS支持处理                            							*
     *                                                                         *
     **************************************************************************/
	
	/*
	 * css可配置属性实现
	 * 
	 */
	
	private static class StyleableProperties {
		
        private static final CssMetaData<LabButton, String> IMAGE =
                new CssMetaData<LabButton,String>("-fx-image",
                    URLConverter.getInstance()) {

                @Override
                public boolean isSettable(LabButton button) {
                    // Note that we care about the image, not imageUrl
                    return button.imageProperty == null || !button.imageProperty.isBound();
                }

                @Override
                public StyleableProperty<String> getStyleableProperty(LabButton button) {
                    return (StyleableProperty<String>)(WritableValue<String>)(button.imageUrlProperty());
                }
            };
            
		// 按钮中图片的宽度配置
		private static final CssMetaData<LabButton,Number> IMAGE_WIDTH =
                new CssMetaData<LabButton,Number>("-fx-image-width",
                SizeConverter.getInstance(), DEFAULT_IMAGE_WIDTH) {

            @Override
            public boolean isSettable(LabButton button) {
            	return button.imageWidthProperty == null || !button.imageWidthProperty.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(LabButton button) {
                return (StyleableProperty<Number>)(WritableValue<Number>)(button.imageWidthProperty());
            }
        };
        
        // 按钮中图片的高度配置
        private static final CssMetaData<LabButton,Number> IMAGE_HEIGHT =
                new CssMetaData<LabButton,Number>("-fx-image-height",
                SizeConverter.getInstance(), DEFAULT_IMAGE_HEIGHT) {

            @Override
            public boolean isSettable(LabButton button) {
            	return button.imageHeightProperty == null || !button.imageHeightProperty.isBound();
            }

            @Override
            public StyleableProperty<Number> getStyleableProperty(LabButton button) {
            	return (StyleableProperty<Number>)(WritableValue<Number>)(button.imageHeightProperty());
            }
        };

        // ------ 将所有样式表添加入 STYLEABLES 列表中 ------ //
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<CssMetaData<? extends Styleable, ?>>(TButton.getClassCssMetaData());
            styleables.add(IMAGE);
            styleables.add(IMAGE_WIDTH);
            styleables.add(IMAGE_HEIGHT);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }
	
	/*
	 * 获取配置数据并处理
	 */
	public final ObjectProperty<Image> imageProperty(){
		if(imageProperty == null) {
			imageProperty = new SimpleObjectProperty<Image>(this,"image");
		}
		return imageProperty;
	}
	
	public Image getImage() {
		return innerImageView.getImage();
	}
	
	public void setImage(Image image) {
		imageProperty().set(image);
	}

	public final StringProperty imageUrlProperty() {
		if(imageUrlProperty == null) {
			imageUrlProperty = new StyleableStringProperty() {
				@Override
				public CssMetaData<? extends Styleable, String> getCssMetaData() {
					return StyleableProperties.IMAGE;
				}

                @Override
                protected void invalidated() {
                	
                    final String imagePath = get();
                    if (imagePath != null) {
                        // 设置一个新图片
                    	imageProperty().set(new Image(imagePath));
                    } else {
                        setImage(null);
                    }
                    
                }
                
				@Override
				public Object getBean() {
					return LabButton.this;
				}

				@Override
				public String getName() {
					return "image-path";
				}
			};
		}
		return imageUrlProperty;
	}
	
	public void setImageURL(String imagePath) {
		imageUrlProperty().set(imagePath);
	}
	
	public String getImageURL() {
		return imageUrlProperty().get();
	}
	
    public final DoubleProperty imageHeightProperty() {
    	if(imageHeightProperty == null) {
    		imageHeightProperty = new SimpleStyleableDoubleProperty(
    				StyleableProperties.IMAGE_HEIGHT,
    				this,
    				"image-height",
    				DEFAULT_IMAGE_HEIGHT);
    	}
        return imageHeightProperty;
    }
    
	public final void setImageHeight(double height) {
		imageHeightProperty().setValue(height);
	}
	
	public final double getImageHeight() {
		return imageHeightProperty.doubleValue();
	}
	
	
    public final DoubleProperty imageWidthProperty() {
    	if(imageWidthProperty == null) {
	    	imageWidthProperty = new SimpleStyleableDoubleProperty(
    				StyleableProperties.IMAGE_WIDTH,
    				this,
    				"image-width",
    				DEFAULT_IMAGE_WIDTH);
    	}
        return imageWidthProperty;
    }
	
	public final void setImageWidth(double width) {
		imageWidthProperty().setValue(width);
	}
	
	public final double getImageWidth() {
		return imageWidthProperty.doubleValue();
	}
	
    /*
     * 将新增的css属性配置器加入列表
     */
    
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }
    
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }
    
    /***************************************************************************
     *                                                                         *
     *                         Getter 和 Setter 方法                            *
     *                                                                         *
     ***************************************************************************/
	
	public ImageView getInnerImage() {
		return innerImageView;
	}
	
	public void setInnerImage(ImageView innerImage) {
		this.innerImageView = innerImage;
	}
	
}
